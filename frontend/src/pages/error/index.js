import React from "react";
import { useParams } from 'react-router-dom';

export default function ErrorPage() {
    const {code} = useParams();

    return (
        <>
            {
                code === '404' ? 
                <h1>Not Found</h1>
                :
                code === '403' ? 
                <h1>Unauthorized</h1>
                :
                <h1>Bad Request</h1>
            }
        </>
    );
}