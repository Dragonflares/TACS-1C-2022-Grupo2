import { get } from "./appService";

const resource = 'languages';

export async function getLanguages(){
    return get(`${resource}`);
}