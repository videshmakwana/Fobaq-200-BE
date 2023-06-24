package com.brilworks.accounts.exception;

public class NotAcceptableException extends BaseException {

    private static final long serialVersionUID = 3928190032776894413L;


    public NotAcceptableException(NotAceptableExeceptionMSG notAceptableExeceptionMSG) {
        super(406, notAceptableExeceptionMSG.getStatusCode(), notAceptableExeceptionMSG.getErrorMessage(), notAceptableExeceptionMSG.getDeveloperMessage());
    }

    public enum NotAceptableExeceptionMSG {
        URL_NOT_SUPPORT_SPECIAL_CHART(
                "4060001",
                "Special Characters Cannot Be Used in the Event URL.",
                "Special Characters Cannot Be Used in the Event URL."),

        ORGANIZER_ALREADY_ASSOCIATED_WITH_EMPLOYEE("4060002",
                "Organizer already associated with employee.",
                "Organizer already associated with employee."
        ),

        ROLE_NAME_NOT_EMPTY("4060002",
                "Role name not empty",
                "Role name not empty"
        ),
        DEPARTMENT_NAME_NOT_EMPTY("4060003",
                "Department name not empty",
                "Department name not empty"
        ),
        DESIGNATION_NAME_NOT_EMPTY("4060004",
                "Designation name not empty",
                "Designation name not empty"
        ),
        DEPARTMENT_ALREADY_STORED_WITH_OTHER_EMPLOYEE("404045", "This department can not be delete, because other employee connected with this department", "This department can not be delete, because other employee connected with this department"),
        EMPLOYEE_NOT_MAPPED_WITH_ORGANISATION("40600003", "Employee not mapped with this organisation", "Employee not mapped with this organisation"),
        WORK_FLOW_VALUE_MISSING("40600004", "Some value missing", "Some value missing"),
        NOTEBOOK_SAVED_IN_SOME_NOTES("40600004", "NoteBook saved in some notes", "NoteBook saved in some notes"),
        NOTE_ID_MISSING("40600005", "Note id missing", "Note id missing");

        private final String statusCode;
        private String errorMessage;
        private String developerMessage;


        NotAceptableExeceptionMSG(String statusCode, String errorMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
            this.developerMessage = errorMessage;
        }


        NotAceptableExeceptionMSG(String statusCode, String errorMessage, String developerMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
            this.developerMessage = developerMessage;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getDeveloperMessage() {
            return developerMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public void setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
        }

    }


}