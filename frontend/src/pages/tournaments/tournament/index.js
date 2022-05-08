import React from "react";
import { useParams } from "react-router-dom";

export function Tournament () {
    const {action, id} = useParams();

    return(
        <div>
            <h2>{action}
                {(action !== 'create')?
                    <>
                        {id}
                    </>
                    :
                    <>
                    </>
                }
                </h2>
        </div>
    )
}

export default Tournament;