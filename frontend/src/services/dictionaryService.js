import {get} from './appService'

const resource = 'dictionary';

export async function getMeaning(word, language){
    return get(`${resource}`, {
        word: word,
        language: language
    }).then(
        async (response) => {
            return {
                word: word,
                meaning: response.data.phrase
            }
        }
    );
}