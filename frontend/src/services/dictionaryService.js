import {get} from './appService'
import { getAuthHeader } from './appService';


const resource = 'dictionary';
const header = `Authorization: Bearer ${getAuthHeader()}`;

export async function getMeaning(word, language){
    return get(`${resource}`, {
        word: word,
        language: language
    }, { headers: { header } } );
}