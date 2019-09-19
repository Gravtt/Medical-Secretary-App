package com.medsec.api;

import com.medsec.filter.Secured;
import com.medsec.util.Database;
import com.medsec.util.UserRole;
import org.glassfish.jersey.server.JSONP;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;


@Path("/")
public class FileAPI {
    @GET
    @Path("file/{pid}")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(
            @Context ServletContext sc,
            @PathParam("pid") String pid){
        try {
            Database db = new Database();
            String link = db.getLink(pid);
            String filepath = sc.getRealPath(link);
            File file = new File(filepath);
            System.out.println(filepath);
            return Response
                    .ok(file,MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition","attachment;filename=" + pid)
                    .build();


        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
