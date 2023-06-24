package com.brilworks.accounts.exception;


public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(NotFound notFound) {
        super(404, notFound.getStatusCode(), notFound.getErrorMessage(), notFound.getDeveloperMessage());
    }

    public NotFoundException(OrganisationNotFound organisationNotFound) {
        super(404, organisationNotFound.getStatusCode(), organisationNotFound.getErrorMessage(), organisationNotFound.getDeveloperMessage());
    }

    public enum NotFound {
        ROLE_NOT_FOUND("4040001", "Role not found", "Role not found"),
        DEPARTMENT_NOT_FOUND("4040002", "Department not found", "Department not found"),
        DESIGNATION_NOT_FOUND("4040003", "Designation not found", "Designation not found"),
        DESIGNATION_ALREADY_PRESENT("4040004", "Designation already present", "Designation already present"),
        EMPLOYEE_NOT_FOUND("4040005", "Employee not found", "Employee not found"),
        EMAIL_NOT_FOUND("4040006", "Email not found", "Email not found"),
        TASKS_NOT_FOUND("4040007", "Tasks not found", "Tasks not found"),
        TASK_NOT_FOUND("4040008", "Task not found", "Task not found"),
        VALUE_MISSING("4040009", "Values missing", "Values missing"),
        EMPLOYEE_LIST_NOT_FOUND("4040010", "Employee list not found", "Employee list not found"),
        TASK_ID_NOT_FOUND("4040011", "Task id not found", "Task id not found"),
        EMPLOYEE_RELATED_ORGANISATION_DATA_NOT_FOUND("404012", "Employee related organisation data not found", "Employee related organisation data not found"),
        BAD_REQUEST("404013", "Bad request", "Bad request"),
        DEPARTMENT_ALREADY_PRESENT("404015", "Department already present", "Department already present"),
        ORGANISATION_NOT_FOUND("404017", "Organisation not found", "Organisation not found"),
        ORGANISATION_EMP_MAPPING_ALREADY_PRESENT("404019", "Employee already connect with this organisation", "Employee already connect with this organisation"),
        LEAVE_NOT_FOUND("404020", "Leave not found", "Leave not found"),
        TASK_ID_MISSING("404021", "Task id missing", "Task id missing"),
        LEAVE_LIST_NOT_FOUND("404022", "Organisation wise leaves not found", "Organisation wise leaves not found"),
        PERMISSION_LIST_NOT_FOUND("404023", "Permision list not found", "Permision list not found"),
        EMAIL_ALREADY_PRESENT_FOUND("404025", "Email already present", "Email already present"),
        SALARY_TEMPLATE_LIST_NOT_FOUND("404027", "Organisation wise salary template list not found", "Organisation wise salary template list not found"),
        SALARY_TEMPLATE_NOT_FOUND("404028", "Salary template not found", "Salary template not found"),
        FILE_LIST_NOT_FOUND("404029", "List of file not found", "List of file not found"),
        INVITED_EMPLOYEE_LIST_NOT_FOUND("404031", "Invited employee list not found", "Invited employee list not found"),
        BANK_INFO_LIST_NOT_FOUND("404032", "List of bank information not found", "List of bank information not found"),
        BANK_INFO_NOT_FOUND("404033", "Employee related bank information not found", "Employee related bank information not found"),
        EMPLOYEE_BANK_INFO_ALREADY_PRESENT("404034", "This employee related bank infromation already save", "This employee related bank infromation already save"),
        ANNOUNCEMENT_NOT_FOUND("404035", "Announcement not found", "Announcement not found"),
        ANNOUNCEMENT_LIST_NOT_FOUND("404036", "Announcement list not found for this organisation", "Announcement list not found for this organisation"),
        TOKEN_NOT_FOUND("404037", "Token not found", "Token not found"),
        PASSWORD_AND_CONFIRM_PASS_NOT_MATCH("404038", "Password and confirm password not match", "Password and confirm password not match"),
        EARNING_LIST_NOT_FOUND("404039", "Earning list not found", "Earning list not found"),
        EARNING_NOT_FOUND("404040", "Earning not found", "Earning not found"),
        EARNING_TYPE_LIST_NOT_FOUND("404041", "Earning type list not found", "Earning type list not found"),
        REIMBURSEMENT_LIST_NOT_FOUND("404042", "Reimbursement list not found", "Reimbursement list not found"),
        REIMBURSEMENT_NOT_FOUND("404044", "Reimbursement not found", "Reimbursement not found"),

        ROLE_ALREADY_SAVED_WITH_OTHER_EMPLOYEE("404046", "This role can not be delete,,because other employee connected with this role ", "This role can not be delete,,because other employee connected with this role"),
        DESIGNATION_ALREADY_SAVED_WITH_OTHER_EMPLOYEE("404047", "This designation can not be delete,because other employee connected with this designation", "This designation can not be delete,because other employee connected with this designation"),
        REPORTING_MANAGER_NOT_FOUND("404048", "Reporting manager not found", "Reporting manager not found"),
        FILE_NOT_FOUND("404049", "File not found", "File not found"),
        EMPLOYEE_DETAIL_NOT_FOUND("404050", "Employee detail not found", "Employee detail not found"),
        EMPLOYEE_DETAIL_LIST_NOT_FOUND("404051", "Employee detail list not found", "Employee detail list not found"),
        EMPLOYEE_HAVING_BIRTHDAY_NOT_FOUND("404052", "In this week not any employee having birthday", "In this week not any employee having birthday"),
        NEW_HIRED_EMPLOYEE_OF_LAST_WEEK_LIST_NOT_FOUND("404053", "New hired employee list not found", "New hired employee list not found"),
        TODAY_NOT_ANY_EMPLOYEE_ON_LEAVE("404054", "Today not any employee on leave", "Today not any employee on leave"),
        TASK_LIST_NOT_FOUND_BY_PROJECT_ID("404055", "Task list not found", "Task list not found"),
        EMP_ASSINGED_TO_SOME_TASK("404056", "Employee can not remove from this project because some tasks already assign to thi employee", "Employee can not remove from this project because some tasks already assign to thi employee"),
        PROJECT_NOT_FOUND("404057", "Project not found", "Project not found"),
        HOLIDAY_NOT_FOUND("404057", "Holiday not found", "Holiday not found"),

        TASK_TEMPLATE_ALREADY_PRESENT("404058", "Task template name already present", "Task template name already present"),
        COMMENT_LIST_NOT_FOUND("404059", "List of comments not found", "List of comments not found"),
        NOTEBOOK_NOT_FOUND("404060", "Notebook not found", "Notebook not found"),
        USER_NOT_FOUND("404061", "User not found!", "User not found!"),
        ROLE_ALREADY_SAVE_WITH_OTHER_USER("404062","role already save with other user","role already save with other user" ),
        USER_ID_MISSING("404063", "User id missing", "User id missing");
        private final String statusCode;
        private final String errorMessage;
        private final String developerMessage;

        NotFound(String statusCode, String errorMessage, String developerMessage) {
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
    }

    public enum OrganisationNotFound {
        ORGANISATION_NOT_FOUND("4040600", "Organisation Not Found", "No Organisation could be found"),
        ORGANISATION_URL_NOT_FOUND("4040601", "Organisation URL not found or Its changed.", "Organisation URL not found or Its changed."),
        USER_NOT_ALLOW_TO_UPDATE("4040602", "User not allow to update.", "User not allow to update.This Organisation is not created by this user"),
        USER_ALREADY_PRESENT_IN_TEAM("4040603", "User already present in team"),
        ORGANISATION_URL_CANNOT_BE_EMPTY("4040604", "Organisation url can't be empty", "Organisation url can't be empty");

        private final String statusCode;
        private final String errorMessage;
        private final String developerMessage;

        OrganisationNotFound(String statusCode, String errorMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
            this.developerMessage = errorMessage;
        }

        OrganisationNotFound(String statusCode, String errorMessage, String developerMessage) {
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
    }
}
