import {get} from './appService'

const resource = 'ditionary';

export async function getMeaning(word, language){
    return get(`${resource}`, {
        word: word,
        language: language
    });
}