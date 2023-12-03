package pt.ipleiria.estg.dei.ei.dae.backend.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;
@Provider
public class MyIllegalArgumentExceptionMapper {

    private static final Logger logger = Logger.getLogger(MyIllegalArgumentExceptionMapper.class.getCanonicalName());

    public Response toResponse(IllegalArgumentException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMsg)
                .build();
    }
}
