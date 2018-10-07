package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.App;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.Profile;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Controller class for the view profile controller. */
public class ProfileScreenController extends Controller {

    /** Text that displays the users first name. */
    @FXML
    private Text firstNameText;

    /** Text that displays the users height. */
    @FXML
    private Text heightText;

    /** Text that displays the users last name. */
    @FXML
    private Text lastNameText;

    /** Text that displays the users weight name. */
    @FXML
    private Text weightText;

    /** Text that displays the year of the users date of birth. */
    @FXML
    private Text yearText;

    /** Text that displays the month of the users date of birth. */
    @FXML
    private Text monthText;

    /** Text that displays the day of the users date of birth. */
    @FXML
    private Text dayText;

    /** Text that displays any errors that occur if a user edits a value incorrectly. */
    @FXML
    private Text errorText;

    /** VBox for first name information. */
    @FXML
    private VBox firstNameVbox;

    /** VBox for last name information. */
    @FXML
    private VBox lastNameVbox;

    /** VBox for height information. */
    @FXML
    private VBox heightVbox;

    /** VBox for weight information. */
    @FXML
    private VBox weightVbox;

    /** VBox for day of date of birth information. */
    @FXML
    private VBox dayVbox;

    /** VBox for month of date of birth information. */
    @FXML
    private VBox monthVbox;

    /** VBox for year of date of birth information. */
    @FXML
    private VBox yearVbox;

    /** Button used to edit and cancel edit of user information. */
    @FXML
    private Button editCancelButton;

    /** ImageView for the users profile picture. */
    @FXML
    private ImageView profilePictureImageView;

    /** Button for changing to the next profile image. */
    @FXML
    private Button nextImagebutton;

    /** Button for changing to the previous profile image. */
    @FXML
    private Button prevImageButton;

    /** TextField for the users first name. */
    private TextField firstNameTextField;

    /** TextField for the users last name. */
    private TextField lastNameTextField;

    /** TextField for the users height. */
    private TextField heightTextField;

    /** TextField for the users weight. */
    private TextField weightTextField;

    /** TextField for the day of the users date of birth. */
    private TextField dayTextField;

    /** TextField for the month of the users date of birth. */
    private TextField monthTextField;

    /** TextField for the year of the users date of birth. */
    private TextField yearTextField;

    /** Boolean that stores whether the user is currently editing. */
    private boolean isEditing = false;

    /** List of all profile pictures the user can choose from */
    private ArrayList<Image> profilePictures;

    /** Index of the current profile image selected. */
    private int currentImageIndex = 0;

    /**
     * Creates a new ProfileScreenController with the given ApplicationStateManager.
     * @param applicationStateManager The ApplicationStateManager of the app.
     */
    public ProfileScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
        profilePictures = new ArrayList<Image>();
        for (int i=0; i < 17; i++) {
            profilePictures.add(new Image(App.class.getResource(String.format("images/profilePictures/ProfilePic%s.png", i)).toString()));
        }
    }

    /** Initializes the profile screen. */
    @FXML
    public void initialize() {
        // Creates new TextFields for all of the different profile attributes.
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        heightTextField = new TextField();
        weightTextField = new TextField();
        dayTextField = new TextField();
        monthTextField = new TextField();
        yearTextField = new TextField();

        // Aligns all the text in the TextFields to center
        firstNameTextField.setAlignment(Pos.CENTER);
        lastNameTextField.setAlignment(Pos.CENTER);
        heightTextField.setAlignment(Pos.CENTER);
        weightTextField.setAlignment(Pos.CENTER);
        dayTextField.setAlignment(Pos.CENTER);
        monthTextField.setAlignment(Pos.CENTER);
        yearTextField.setAlignment(Pos.CENTER);

        // Load the users profile picture
        URL profileImageUrl = App.class.getResource(applicationStateManager.getCurrentProfile().getPictureURL());
        if (profileImageUrl != null) {
            Image userProfileImage = GuiUtilities.maskProfileImage(new Image(profileImageUrl.toString()));
            profilePictureImageView.setImage(userProfileImage);
            Pattern p = Pattern.compile("ProfilePic([0-9]+)");
            Matcher m = p.matcher(profileImageUrl.toString());
            if (m.find()) {
                currentImageIndex = Integer.parseInt(m.group(1));
            }

            if (currentImageIndex >= profilePictures.size()) {
                currentImageIndex = 0;
            }
        }

        // Disables the previous and next image buttons.
        prevImageButton.setVisible(false);
        nextImagebutton.setVisible(false);
    }

    /**
     * Action performed when the user clicks done.
     * If the user is editing then this saves their edits and changes all the TextFields to Text.
     * If the user is not editing then the profile screen is closed.
     */
    @FXML
    public void done() {
        if (isEditing) {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String weightString = weightTextField.getText();
            String heightString = heightTextField.getText();

            String dayString = dayTextField.getText();
            String monthString = monthTextField.getText();
            String yearString = yearTextField.getText();

            String dateString = String.format("%4s-%2s-%2s", yearString, monthString, dayString).replace(' ', '0');

            // Try to parse the date string to check that it is in a valid format.
            LocalDate dateOfBirth = null;
            boolean isValidDateFormat = false;
            try {
                dateOfBirth = LocalDate.parse(dateString);
                isValidDateFormat = true;
            } catch (Exception e) {
                isValidDateFormat = false;
            }

            double weight = 0.0;
            boolean isValidWeightDoubleString = false;
            try {
                weight = Double.parseDouble(weightString);
                isValidWeightDoubleString = true;
            } catch (Exception e) {
                isValidWeightDoubleString = false;
            }

            // Try to parse the height string to check that it is in a valid format for a double.
            double height = 0.0;
            boolean isValidHeightDoubleString = false;
            try {
                height = Double.parseDouble(heightString);
                isValidHeightDoubleString = true;
            } catch (Exception e) {
                isValidHeightDoubleString = false;
            }

            // Check that the given values are valid. If they are not, the user is displayed an appropriate error message.
            if (!Profile.isValidName(firstName)) {
                errorText.setText(String.format("First name must be between %s and %s characters.", Profile.MIN_NAME_SIZE, Profile.MAX_NAME_SIZE));
            } else if (!Profile.isValidName(lastName)){
                errorText.setText(String.format("Last name must be between %s and %s characters.", Profile.MIN_NAME_SIZE, Profile.MAX_NAME_SIZE));
            } else if (!isValidDateFormat) {
                errorText.setText("Date should be in the form dd/mm/yyyy.");
            } else if (!Profile.isValidDateOfBirth(dateOfBirth)) {
                errorText.setText(String.format("Date should be between %s and %s.", Profile.MIN_DOB, LocalDate.now()));
            } else if (!isValidWeightDoubleString || !Profile.isValidWeight(weight)) {
                errorText.setText(String.format("Weight must be a number between %s and %s", Profile.MIN_WEIGHT, Profile.MAX_WEIGHT));
            } else if (!isValidHeightDoubleString || !Profile.isValidHeight(height)) {
                errorText.setText(String.format("Height must be a number between %s and %s", Profile.MIN_HEIGHT, Profile.MAX_HEIGHT));
            } else {
                Profile profile = applicationStateManager.getCurrentProfile();

                try {
                    profile.setFirstName(firstName);
                    profile.setLastName(lastName);
                    profile.setWeight(weight);
                    profile.setHeight(height);
                    profile.setDateOfBirth(dateString);
                    profile.setPictureURL(String.format("images/profilePictures/ProfilePic%s.png", currentImageIndex));

                    updateInformation();
                    changeValuesToText();
                    editCancelButton.setText("Edit");
                    isEditing = false;
                    prevImageButton.setVisible(false);
                    nextImagebutton.setVisible(false);
                    errorText.setText("");
                } catch (java.sql.SQLException e) {
                    GuiUtilities.displayErrorMessage("Error encountered editing profile.", e.getMessage());
                    e.printStackTrace();
                }
            }

        } else {
            ((MainScreenController) applicationStateManager.getScreenController("MainScreen")).updateProfileButton();
            applicationStateManager.switchToScreen("MainScreen");
        }
    }

    /**
     * Action performed when the user clicks the edit/cancel button.
     * If the user is not editing, then they enter edit mode and all the Text of attributes changes to TextFields.
     * If the user is editing then the TextFields are changed to Text and any changes made to profile attributes are
     * reverted.
     */
    @FXML void editProfile() {
        if (!isEditing){
            //Changing all text to text fields so that the user can edit.

            firstNameTextField.setText(applicationStateManager.getCurrentProfile().getFirstName());
            lastNameTextField.setText(applicationStateManager.getCurrentProfile().getLastName());
            heightTextField.setText(Double.toString(applicationStateManager.getCurrentProfile().getHeight()));
            weightTextField.setText(Double.toString(applicationStateManager.getCurrentProfile().getWeight()));
            dayTextField.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getDayOfMonth()));
            monthTextField.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getMonthValue()));
            yearTextField.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getYear()));

            firstNameVbox.getChildren().setAll(firstNameTextField);
            lastNameVbox.getChildren().setAll(lastNameTextField);
            heightVbox.getChildren().setAll(heightTextField);
            weightVbox.getChildren().setAll(weightTextField);
            dayVbox.getChildren().setAll(dayTextField);
            monthVbox.getChildren().setAll(monthTextField);
            yearVbox.getChildren().setAll(yearTextField);

            prevImageButton.setVisible(true);
            nextImagebutton.setVisible(true);

            editCancelButton.setText("Cancel");
            isEditing = true;
        } else  {
            editCancelButton.setText("Edit");
            isEditing = false;
            updateInformation();
            changeValuesToText();

            prevImageButton.setVisible(false);
            nextImagebutton.setVisible(false);
            errorText.setText("");
        }

    }


    /**
     * Updates the information being displayed as text to the current values of the users profile.
     */
    public void updateInformation() {
        firstNameText.setText(applicationStateManager.getCurrentProfile().getFirstName());
        lastNameText.setText(applicationStateManager.getCurrentProfile().getLastName());
        heightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getHeight()));
        weightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getWeight()));
        dayText.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getDayOfMonth()));
        monthText.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getMonthValue()));
        yearText.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getYear()));

        URL profileImageUrl = App.class.getResource(applicationStateManager.getCurrentProfile().getPictureURL());
        if (profileImageUrl != null) {
            profilePictureImageView.setImage(GuiUtilities.maskProfileImage(new Image(profileImageUrl.toString())));
        }
    }

    /**
     * Changes all of the TextFields to Text.
     */
    private void changeValuesToText() {
        firstNameVbox.getChildren().setAll(firstNameText);
        lastNameVbox.getChildren().setAll(lastNameText);
        heightVbox.getChildren().setAll(heightText);
        weightVbox.getChildren().setAll(weightText);
        dayVbox.getChildren().setAll(dayText);
        monthVbox.getChildren().setAll(monthText);
        yearVbox.getChildren().setAll(yearText);
    }

    /**
     * Action that is performed when the user clicks the delete profile button.
     * Displays a prompt asking the user if they want to delete there profile.
     */
    @FXML
    public void deleteProfile() {
        Pane deletionPopup = GuiUtilities.loadPane("ProfileDeletionConfirmation.fxml", new ProfileDeletionConfirmationController(applicationStateManager));
        applicationStateManager.displayPopUp(deletionPopup);
    }

    /**
     * Changes to the next profile picture to view.
     */
    @FXML
    public void prevImage() {
        currentImageIndex -= 1;
        if (currentImageIndex < 0) {
            currentImageIndex = profilePictures.size()-1;
        }
        profilePictureImageView.setImage(GuiUtilities.maskProfileImage(profilePictures.get(currentImageIndex)));
    }

    /**
     * Changes to the previous profile picture to view.
     */
    @FXML
    public void nextImage() {
        currentImageIndex += 1;
        if (currentImageIndex >= profilePictures.size()) {
            currentImageIndex = 0;
        }
        profilePictureImageView.setImage(GuiUtilities.maskProfileImage(profilePictures.get(currentImageIndex)));
    }


}
