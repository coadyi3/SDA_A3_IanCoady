package com.example.sda_a3_iancoady;


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
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/*
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan 2019
 */
public class OrderTshirt extends Fragment {


    public OrderTshirt() {
        // Required empty public constructor
    }

    //class wide variables
    private Spinner             mSpinner;
    private EditText            mCustomerName;
    private EditText            meditDelivery;
    private RadioGroup          deliveryOption;
    private RadioButton         collection;
    private RadioButton         Delivery;
    private ImageView           mCameraImage;
    private int                 cameraClicked = 0;
    private TextView            deliveryText;
    private String              currentPhotoPath;
    private File                photoFile = null;
    //static keys
    private static final int    REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = "OrderTshirt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment get the root view.
        final View root = inflater.inflate(R.layout.fragment_order_tshirt, container, false);

        mCustomerName = root.findViewById(R.id.editCustomer);
        meditDelivery = root.findViewById(R.id.editDeliver);
        meditDelivery.setImeOptions(EditorInfo.IME_ACTION_DONE);
        meditDelivery.setRawInputType(InputType.TYPE_CLASS_TEXT);
        deliveryOption = root.findViewById(R.id.deliveryOptions);
        collection = root.findViewById(R.id.collectionRadio);
        Delivery = root.findViewById(R.id.deliveryRadio);
        deliveryText = root.findViewById(R.id.editCollect);

        mCameraImage = root.findViewById(R.id.imageView);;
        Button mSendButton = root.findViewById(R.id.sendButton);

        //set a listener on the the camera image
        mCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraClicked++;
                dispatchTakePictureIntent(v);
            }
        });

        //set a listener to start the email intent.
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(mCustomerName.getText().toString()))    sendEmail(v);
                else                                                    Toast.makeText(getContext(), "Please enter a valid Email", Toast.LENGTH_SHORT).show();
            }
        });

        deliveryOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(collection.isChecked()){
                    meditDelivery.setVisibility(View.INVISIBLE);
                    mSpinner.setVisibility(View.VISIBLE);
                    deliveryText.setVisibility(View.VISIBLE);
                }

                else if(Delivery.isChecked()){
                    meditDelivery.setVisibility(View.VISIBLE);
                    mSpinner.setVisibility(View.INVISIBLE);
                    deliveryText.setVisibility(View.INVISIBLE);
                }
            }
        });

        //initialise spinner using the integer array
        mSpinner = root.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_time_entries, R.layout.spinner_days);
        mSpinner.setAdapter(adapter);
        mSpinner.setEnabled(true);

        return root;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if(image != null){currentPhotoPath = image.getAbsolutePath();}
        Log.i(TAG, "createImageFile: "+ currentPhotoPath);
        return image;
    }

    //Take a photo note the view is being passed so we can get context because it is a fragment.
    //update this to save the image so it can be sent via email
    private void dispatchTakePictureIntent(View v)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(v.getContext().getPackageManager()) != null) {

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(v.getContext(),
                        "com.example.sdaassign32019johndoe.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            mCameraImage.setImageBitmap(imageBitmap);
        }
    }

    /*
     * Returns the Email Body Message, update this to handle either collection or delivery
     */
    private String createOrderSummary(View v)
    {
        String orderMessage = "";
        String customerName =           mCustomerName.getText().toString();

        if(collection.isChecked()){

            orderMessage += "\n" + getString(R.string.order_message_1);
            orderMessage += "\n" + getString(R.string.order_message_collect) + mSpinner.getSelectedItem().toString() + " day(s)";
            orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + customerName;
        }

        if(Delivery.isChecked()){

            orderMessage += "\n" + getString(R.string.order_message_1);
            orderMessage += "\n" + getString(R.string.order_message_delivered);
            orderMessage += "\n" + meditDelivery.getText().toString() + "\n";
            orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + customerName;

        }

        return orderMessage;
    }

    //Update me to send an email
    private void sendEmail(View v)
    {
        //check that Name is not empty, and ask do they want to continue
        String customerName = mCustomerName.getText().toString();
        if (mCustomerName == null || customerName.equals("") ||  cameraClicked == 0)
        {
            Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();

            /* we can also use a dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification!").setMessage("Customer Name not set.").setPositiveButton("OK", null).show();*/

        } else {
            Log.d(TAG, "sendEmail: should be sending an email with "+createOrderSummary(v));

            Intent intent = new Intent(Intent.ACTION_SENDTO);

            intent.putExtra(Intent.EXTRA_SUBJECT, "TShirt Order");
            intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(v));
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
            intent.setData(Uri.parse("mailto: sdaTshirts@dcu.ie"));

            startActivity(intent);
        }
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
