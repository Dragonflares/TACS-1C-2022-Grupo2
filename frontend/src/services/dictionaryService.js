import {get} from './appService'
import { getAuthHeader } from './appService';


const resource = 'meanings';

export async function getMeaning(word, language){
    return get(`${resource}`, {
        word: word,
        language: language
    });
}