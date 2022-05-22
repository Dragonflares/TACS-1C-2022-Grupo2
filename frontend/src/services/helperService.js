import {get, post} from './appService'

const resource = 'helper'

export async function getHelperWord(data){
    return post(`${resource}`, data)
}