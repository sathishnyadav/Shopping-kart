package com.testyantraglobal.shopping.shoppingkarttyapi.util;

public interface ApplicationConstants {
	String SUCCESS = "Success";
	String ID_NOT_FOUND = "Give ID does not exist";
	String CREATED = "Created";
	String NO_USER = "USER NOT SAVED";
	int SIZE = 45;
	String EMAIL_NOT_SENT = "Email Not Sent";
	String TEMPLATE_NOT_FOUND = "Template is not found";
	String MALTEMPLTE = "Template is not named properly";
	String TEMPLATE = "email-template.ftl";
	String SUBJECT = "WELCOME ONBOARD";
	String FORGOT_PASSWORD = "Reset Password";
	String RESET_PASSWORD_TEMPLATE = "resetpassword-email.ftl";
	String RESET_URL = "/user/reset_password?token=";
	String FORGOT_URL = "/user/verify_account?token=";
	String DELETED_USER = "User Removed from the DB";
	String INACTIVE_USER = "User is not Activated";
	String NO_PRODUCT = "PRODUCT NOT SAVED";
	String TYPE_NOT_FOUND = "The product with given type not found";
	String BRAND_NOT_FOUND = "The product with given brand not found";
}
