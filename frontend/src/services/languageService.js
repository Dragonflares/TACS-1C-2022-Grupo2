import { get } from "./appService";

const resource = 'languages';

export async function getLangauges(){
    // cuando se implmente en la api
    //return get(`resource`);

    return {
        data: [
            {
                id: 'en',
                desc: 'English'
            },
            {
                id: 'es',
                desc: 'Español'
            }
        ],
        status: 200,
    };
}