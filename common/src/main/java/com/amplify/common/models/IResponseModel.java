package com.amplify.common.models;


import com.amplify.common.errors.ErrorCompact;

public interface IResponseModel<T>
{
    String getMessage ();
    T getData ();
    String getTimeStamp ();
    ErrorCompact getError ();
}
