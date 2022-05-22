import { get } from "./appService";

const resource = 'privacy';

export async function getPrivacies(){
    return get(`${resource}`);
    
}