package pt.ipleiria.estg.dei.ei.dae.backend.exceptions.mappers;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.logging.Logger;

public class MyConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = Logger.getLogger(ConstraintViolationException.class.getCanonicalName());

    @Override
    public Response toResponse(ConstraintViolationException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMsg)
                .build();
    }

}
