package si.fri.prpo.skupina5.mappers;

import si.fri.prpo.skupina5.izjeme.NeveljavnaKosaricaException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NeveljavnaKosaricaExceptionMapper implements ExceptionMapper<NeveljavnaKosaricaException> {

    @Override
    public Response toResponse(NeveljavnaKosaricaException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\": \"" + e.getMessage() + "\"}")
                .build();
    }
}
