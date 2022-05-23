import {get, post} from './appService'

const resource = 'help'

export async function getHelperWord(data){
    return post(`${resource}`, data)
}