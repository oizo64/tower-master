package ppl.pl.tower.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ErrorDTO {
    public String title;
    public String detail;
    public int status;
    public String errorType;
    public String errorCode;
    public String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z Z"));

    public ErrorDTO(String title, String detail, int status, String errorType, String errorCode) {
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    public static ErrorDTOBuilder builder() {
        return new ErrorDTOBuilder();
    }

    public static class ErrorDTOBuilder {
        public String title;
        public String detail;
        public int status;
        public String errorType;
        public String errorCode;

        public ErrorDTOBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public ErrorDTOBuilder withDetails(final String detail) {
            this.detail = detail;
            return this;
        }

        public ErrorDTOBuilder withStatus(final int status) {
            this.status = status;
            return this;
        }

        public ErrorDTOBuilder withErrorType(final String errorType) {
            this.errorType = errorType;
            return this;
        }

        public ErrorDTOBuilder withErrorCode(final String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorDTO build() {
            return new ErrorDTO(title, detail, status, errorType, errorCode);
        }
    }
}
