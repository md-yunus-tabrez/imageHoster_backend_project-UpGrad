# imageHoster_backend_project-UpGrad

![imageHoster](https://user-images.githubusercontent.com/61626607/119570980-6d52fd00-bdce-11eb-9f51-c62395be6ae1.png)


<h2>This is related to the development of REST API endpoints in the following controllers:</h2>

  <h3>Signup-controller:</h3>In this controller, the user will able to sign up for an account.
  <h3>Authentication-controller:</h3>After signing up, the user needs to sign in. This controller authenticates the user based on the credentials provided. After authentication,      the user will be given an ‘access token’, which will be required to perform any further operation.
  <h3>Image-upload-controller:</h3>Using the ‘access token’, the user can upload an image through this controller. But the image is not ‘ACTIVE’ until reviewed by the admin.
  <h3>Admin-controller:</h3>
  
  The admin needs to review all the images uploaded by users. This controller provides the admin with the details about all the images. Once the admin has reviewed the               image, he can update it (if needed) and make it ‘ACTIVE’ through this controller.
