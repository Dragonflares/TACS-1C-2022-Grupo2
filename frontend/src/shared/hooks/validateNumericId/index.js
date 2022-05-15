import React from "react";

export function useValidateNumericId(id){
    return !isNaN(parseInt(id));
}