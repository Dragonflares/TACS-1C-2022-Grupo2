import axios from 'axios';
import {put, get, post} from './appService';
import Config from '../appConfig.json';

const url = Config.ApiURL;

const resource='users';
const resultSubResource='results';
const tournamentsSubResource='tournaments';

export async function createUser(data){
    return axios.post(`${url}/authorization`, data);
}

export async function updateUser(data){
    return put(`${resource}/${data.id}`, data); 
}

export async function getTournaments(userId, offset, limit){
    return get(`${resource}/${userId}/${tournamentsSubResource}`, {
        offset: offset,
        limit: limit
    });
}

export async function getDailyResults(userId){
    return get(`${resource}/${userId}/${resultSubResource}`);
}

export async function updateDailyResults(userId, data){
    return put(`${resource}/${userId}/${resultSubResource}`, data);
}