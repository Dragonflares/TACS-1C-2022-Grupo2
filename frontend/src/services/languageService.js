import { get } from "./appService";

const resource = 'languages';

export async function getLangauges(){
    // cuando se implmente en la api
    //return get(`resource`);

    return {
        data: [
            {
                id: 'ENGLISH',
                desc: 'English'
            },
            {
                id: 'SPANISH',
                desc: 'Español'
            }
        ],
        status: 200,
    };
}