package com.grostory.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.grostory.exception.GrostoryException;

public class GrostoryExceptionMapper implements ExceptionMapper<GrostoryException> 
{
	@Override
	public Response toResponse(GrostoryException ex)
	{
		return Response.status(Response.Status.BAD_REQUEST).entity(ex.getExceptionMessage()).build();
	}
}
