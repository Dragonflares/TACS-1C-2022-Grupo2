import { get } from "./appService";

const resource = 'privacys';

export async function getPrivacies(){
    // cuando se implmente en la api
    //return get(`resource`);

    return {
        data: {
            response: [
                {
                    id: 'PRIVATE',
                    desc: 'PRIVATE'
                },
                {
                    id: 'PUBLIC',
                    desc: 'PUBLIC'
                }
            ]
        },
        status: 200,
    };
}