package com.ltrha.ticket.constant.enums;

public class CommonEnums {
    public enum MailTemplate {
        ACCOUNT_ACTIVATION("account-activation"),
        BOOKING_SUCCESSFUL("booking-successful"),
        RESET_PASSWORD("reset-password");
        private final String templateName;

        MailTemplate(String templateName) {
            this.templateName = templateName;
        }

        public String getTemplateName() {
            return templateName;
        }

    }
}
