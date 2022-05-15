import React from "react";

export function useValidateMode(mode){
    return mode === 'view' || mode === 'create' || mode === 'edit' || mode === 'delete'
}
