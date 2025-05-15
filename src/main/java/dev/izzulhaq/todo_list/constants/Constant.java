package dev.izzulhaq.todo_list.constants;

public class Constant {
    public static final String BASE_API = "/api/v1";

    public static final String AUTH_API = BASE_API + "/auth";
    public static final String ADMIN_REGISTER = "/register/admin";
    public static final String USER_REGISTER = "/register/user";
    public static final String LOGIN = "/login";

    public static final String USER_ACCOUNT_API = BASE_API + "/users";
    public static final String TODO_API = BASE_API + "/todos";

    public static final String USING_ID_ENDPOINT = "/{id}";

    public static final String RESCHEDULE_ENDPOINT = USING_ID_ENDPOINT + "/reschedule";
    public static final String CANCEL_ENDPOINT = USING_ID_ENDPOINT + "/cancel";
    public static final String FINISH_ENDPOINT = USING_ID_ENDPOINT + "/finish";

    public static final String REGISTER_MESSAGE = "Successfully register new ";
    public static final String REGISTER_MESSAGE_ADMIN = REGISTER_MESSAGE + "admin.";
    public static final String REGISTER_MESSAGE_USER = REGISTER_MESSAGE + "user.";
    public static final String LOGIN_MESSAGE = "Logged in successfully.";

    public static final String CREATE_MESSAGE = " created Successfully.";
    public static final String GET_MESSAGE = "Successfully retrieved ";
    public static final String GET_ALL_MESSAGE = GET_MESSAGE + "all ";
    public static final String UPDATE_MESSAGE = "Successfully update ";
    public static final String DELETE_MESSAGE = "Successfully delete ";

    public static final String GET_USER_BY_ID_MESSAGE = GET_MESSAGE + "user data.";
    public static final String GET_ALL_USER_MESSAGE = GET_ALL_MESSAGE + "user data.";
    public static final String UPDATE_PASSWORD_MESSAGE = UPDATE_MESSAGE + "user's password data.";
    public static final String DELETE_USER_MESSAGE = DELETE_MESSAGE + "user data.";

    public static final String CREATE_TODO_MESSAGE = "Todo" + CREATE_MESSAGE;
    public static final String GET_TODO_BY_ID_MESSAGE = GET_MESSAGE + "todo data.";
    public static final String GET_ALL_TODO_MESSAGE = GET_ALL_MESSAGE + "todo data.";
    public static final String UPDATE_TODO_MESSAGE = UPDATE_MESSAGE + "todo data.";
    public static final String RESCHEDULE_TODO_MESSAGE = "Successfully reschedule todo.";
    public static final String CANCEL_TODO_MESSAGE = "Successfully cancel todo.";
    public static final String FINISH_TODO_MESSAGE = "Successfully finish todo.";
    public static final String DELETE_TODO_MESSAGE = DELETE_MESSAGE + "todo data.";

    public static final String DUPLICATE_USER_ERROR_MESSAGE = "Username already exist.";
    public static final String NOT_FOUND_ERROR_MESSAGE = " not found.";
    public static final String USER_NOT_FOUND = "User Account" + NOT_FOUND_ERROR_MESSAGE;
    public static final String TODO_NOT_FOUND = "Todo" + NOT_FOUND_ERROR_MESSAGE;
    public static final String WRONG_PASSWORD = "Wrong password.";
    public static final String DELETE_USER_ERROR_MESSAGE = "User Account has already deleted.";

    public static final String RESCHEDULED = "rescheduled";
    public static final String CANCELED = "canceled";
    public static final String FINISHED = "finished";

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = DATE_PATTERN + " HH:mm:ss";
}
