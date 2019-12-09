package com.example.sda_a3_iancoady;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * <h2>
 *     This class file is one of 3 fragments of the entire application, this class in particular focuses on allowing the user to order a tshirt design of their own making and send the order details
 *     onto the manufacturer or retailer.<br>
 *     The GUI consists of an email entry, imageView editor, a radio group for selecting how the user will obtain the tshirt and according entries based on the users decision. There is also a send
 *     button which will send the selected details to an email client of the users choosing to send the order to the manufacturer.
 * </h2>
 *
 * <p>This is verison 2.0 as this is an adaptation of Chirs Coughlin's original app</p>
 *
 * @link {SDA Course Assignments}
 *
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan 2019
 * @author Ian Coady 2019
 * @since 2019-12-04
 * @version 2.0
 */
public class OrderTshirt extends Fragment {


    public OrderTshirt() {
        // Required empty public constructor
    }

    //class wide variables
    private Spinner             tSpinner;
    private EditText            tCustomerName;
    private EditText            tEditDelivery;
    private RadioButton         collection;
    private RadioButton         delivery;
    private ImageView           tCameraImage;
    private int                 cameraClicked = 0;
    private TextView            deliveryText;
    private String              currentPhotoPath;
    private File                photo = null;
    //static keys
    private static final int    REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = "OrderTshirt";


    /**
     * <h1>OnCreateView</h1>
     * <p>
     *      Once this fragment is opened this activity file is called and beings by called onCreateView which sets the GUI for this fragment of the PagerView.<br>
     *      Once the GUI has been inflated and visible to the user, the widgets inside the GUI need to be initialized so that listeners can be set and layout files can be set for example
     *      the Spinner in our layout has a layout of its own so the spinner needs to be initialized in order to set its layout in the GUI.<br><br>
     *
     *      Following this some listeners are set on the RadioGroup, ImageView and Send Button in order to set their functionality.<br>
     *      The radio group determines what method of obtaining the Tshirt will occur, the image view will set a picture to be printed on the tshirt and the send button will open the email client
     *      with the details already populated in the email ready to send.
     * </p>
     *
     * @param inflater                 inflater for the root view to set the GUI of this fragment.
     * @param container                Parent container the fragment is located in.
     * @param savedInstanceState       Current state of the GUI.
     * @return root                    Returns the loaded fragment GUI to the device.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment get the root view.
        final View root                 = inflater.inflate(R.layout.fragment_order_tshirt, container, false);

        tCustomerName                   = root.findViewById(R.id.editCustomer);
        tEditDelivery                   = root.findViewById(R.id.editDeliver);
        tEditDelivery                   .setImeOptions(EditorInfo.IME_ACTION_DONE);
        tEditDelivery                   .setRawInputType(InputType.TYPE_CLASS_TEXT);
        RadioGroup deliveryOption       = root.findViewById(R.id.deliveryOptions);
        collection                      = root.findViewById(R.id.collectionRadio);
        delivery                        = root.findViewById(R.id.deliveryRadio);
        deliveryText                    = root.findViewById(R.id.editCollect);
        tCameraImage                    = root.findViewById(R.id.imageView);
        Button mSendButton              = root.findViewById(R.id.sendButton);

        //set a listener on the the camera image
        tCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(v);
            }
        });

        //set a listener to start the email intent.
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        //set a listener to see which delivery option is selected
        deliveryOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(collection.isChecked()){
                    tEditDelivery.setEnabled(false);
                    tEditDelivery.setVisibility(View.INVISIBLE);
                    tSpinner.setVisibility(View.VISIBLE);
                    deliveryText.setVisibility(View.VISIBLE);
                }

                else if(delivery.isChecked()){
                    tEditDelivery.setEnabled(true);
                    tEditDelivery.setVisibility(View.VISIBLE);
                    tSpinner.setVisibility(View.INVISIBLE);
                    deliveryText.setVisibility(View.INVISIBLE);
                }
            }
        });

        //initialise spinner using the integer array
        tSpinner = root.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_time_entries, R.layout.spinner_days);
        tSpinner.setAdapter(adapter);
        tSpinner.setEnabled(true);

        return root;
    }

    /**
     * <h1>Creating an file in storage.</h1>
     * <p>
     *     This method gets called by the camera activity to create an empty file in local storage to be used by the camera intent to save the image taken by the user forever.<br>
     *     Each filename is unique because it is named based on the timestamp the file was created on which cannnot be duplicated unless hard coded, this prevents overwriting of files and images.
     * </p>
     * 
     * 
     * @return imageTaken       Returns an empty file with a location on the devices storage for use by other activities or intents.                 
     * @throws IOException      Exception thrown if a file cannot be created.
     * @see FileProvider        Example from course text
     * @since 2019
     */
    private File createImageFile() throws IOException {
        // Create an imageTaken file name
        String timeStampFileName    = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName        = "JPEG_" + timeStampFileName + "_";

        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageTaken = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir     /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if(imageTaken != null){currentPhotoPath = imageTaken.getAbsolutePath();}
        Log.i(TAG, "createImageFile: "+ currentPhotoPath);
        return imageTaken;
    }

    /**
     * <h1>Taking a picture with the camera</h1>
     * <p>When the image view where the photo is located is clicked, this method is called to open the camera for image capture.<br>
     *    Once the image is taken, the user is allowed to either approve the photo to send to the image view or to take the photo again.<br>
     *    While the camera is being opened, the createImageFile() method ie being called to create a filename and space for the image on the devices memory using @link{FileProvider}.<br>
     *    The empty file is returned to the camera activity and the image is given that associated filename and storage location and stored on the device permanently.
     * </p>
     *
     * @param v     The view being passed to the camera activity.
     * @see <a href="https://developer.android.com/training/camera/photobasics">See here for futher details on Camera intents.</a>
     * @author Unknown
     */
    private void dispatchTakePictureIntent(View v)
    {
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhoto.resolveActivity(v.getContext().getPackageManager()) != null) {

            try {
                photo = createImageFile();
                Log.i(TAG, "dispatchTakePictureIntent: File Created");
            } catch (IOException ex) {
                Log.i(TAG, "dispatchTakePictureIntent: File Creation Failed");            }
            // Continue only if the File was successfully created
            if (photo != null) {
                Uri photoURI = FileProvider.getUriForFile(v.getContext(),
                        "com.example.sdaassign32019johndoe.fileprovider",
                        photo);
                takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePhoto, REQUEST_TAKE_PHOTO);
        }
    }
    }

    /**
     * <h1>Camera Activity Result</h1>
     * <p>On completion of the camera activity, this method is called to handle the picture data,
     * in our case we want to take the saved image taken by the camera and populate an imageView field with it.</p>
     *
     * @param requestCode       an integer value passed from the camera activity upon completion. Used to validate what activity took place before onActivityResult was called.
     * @param resultCode        An integer values used to determine whether the previous activity took place successfully.
     * @param data              Intent bundle data that can be taken from the previous activity, not used in our case so <code>= null;</code>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            tCameraImage.setImageBitmap(imageBitmap);
            cameraClicked++;
        }
    }

    /**
     * <h1>Creating the email body text</h1>
     * <p>Method to format the email body based on the option of delivery of the tshirt selected.
     *  It takes a string value called orderMessage and adds internationalized text to it based on
     *  the selected option.</p>
     *
     * @return orderMessage to the email client after formatting based on selected option.
     * @author Ian Coady
     */
    private String createOrderSummary()
    {
        String orderMessage = "";
        String customerName =           tCustomerName.getText().toString();

        if(collection.isChecked()){

            orderMessage += "\n" + getString(R.string.order_message_1);
            orderMessage += "\n" + getString(R.string.order_message_collect) + tSpinner.getSelectedItem().toString() + getString(R.string.days) + "\n";
            orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + customerName;
        }

        if(delivery.isChecked()){

            orderMessage += "\n" + getString(R.string.order_message_1);
            orderMessage += "\n" + getString(R.string.order_message_delivered);
            orderMessage += "\n" + tEditDelivery.getText().toString() + "\n";
            orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + customerName;

        }

        return orderMessage;
    }

    /**
     * <h1>Send Email Method</h1>
     * <p>Checks if the user has input correct values for their email and taken a photo and sends the data to an email client of the users choosing.<br>
     *    Calls the createOrderSummary Method to format the body of the email accordingly.<br>
     *    Following the completion of createOrderSummary, add's the text, saved image and subject text to the intent and begins the intent.<br>
     *    If the criteria for sending the email are not met, a Toast message will inform the user of such.
     *
     * @author Ian Coady
     * @since 2019-12-01
     */
    private void sendEmail()
    {
        //check that Name is not empty, and ask do they want to continue
        String customerName = tCustomerName.getText().toString();
        if (tCustomerName == null || customerName.equals("") ||  cameraClicked == 0){
            Toast.makeText(getContext(), getString(R.string.Alert), Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d(TAG, "sendEmail: should be sending an email with "+createOrderSummary());

            Intent sendEmail = new Intent(Intent.ACTION_SENDTO);

            sendEmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_subject));
            sendEmail.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
            sendEmail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photo));
            sendEmail.setData(Uri.parse("mailto: " + getString(R.string.to_email)));

            startActivity(sendEmail);
        }
    }
}
