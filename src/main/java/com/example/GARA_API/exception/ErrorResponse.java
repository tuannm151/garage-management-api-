package com.example.GARA_API.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp = new Date();
    private int status;
    private HttpStatus error;
    private String message;
    private String path;
    //General error message about nature of error


    //Specific errors in API request processing
    private List<String> detail;

    //Getter and setters
    public ErrorResponse(HttpStatus status) {
        super();
        this.status = status.value();
        this.error = status;
    }
}