import React from "react";
import { useNavigate } from "react-router-dom";

export function useHandleHttpResponse(callback, status) {
       
    if(status === 200){
        return callback;
    }
    else{
        return () => {
            /*  preguntar como redireccionar desde un hook o un workarround
                const navigate = useNavigate();
                navigate(`error/${status}`)
            */
           console.log(`Error ${status}`);
        };
    }
}