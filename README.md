# SDA_A3_IanCoady

This Assignment was a project in the latest Units of the SDA module in DCU but also an exercise in adaptation of someone elses original codebase. It is a basic application which outlines the usage of recycler vies, pager views and their relative tabs in Android App development. 

Also this assignment examines the students understanding of the FileProvider class in Android and how it can be used to return files from different existing android aplications, in this case a Camera activity.

Any and all code adapted from third parties are referenced inside of the code itself in comments or in the JavaDoc Files.

Special mention to developers.android.com who was my main resource for this assignment, the relative licence for adapting this code is found in the activity files themselves.
Also another special mention to pixabay.com who's royalty free images allowed be to illulstrate the recycler view for this assignment effectivly.


This application contains three seperate tabs contained inside one parent main acitivyt - A welcome screen, a products screen and an order screen.

The Welcome activity contains a standard welcome message and a toast message to instruct the user to swipe to view other tabs.

The Products tab is a recycler view list of t shirt styles and colors, if the user taps on one of the styles, a toast message appears describing the shirt selected and the relative price of it.

Finally, the Order tab is a form containing an EditText for the users name, an image view which when clicked opens up the camera and returns the photo taken to the camera. It also allowes the user to choose how they will obtain the tshirt whether its by collection or delivery, GUI options are available to the user based on their choice.
Once the user has entered their name, taken a photo and entered their address, they can proceed with their order by pressing the send button which will open an email client and populate the email body ready to send to the distributer.


