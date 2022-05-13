import React from "react";
import { useNavigate } from "react-router-dom";

export function useHandleHttpResponse(callback, status) {
    const navigate = useNavigate();
    
    if(status === 200){
        callback();
    }
    else{
        navigate(`error/${status}`);
    }
}