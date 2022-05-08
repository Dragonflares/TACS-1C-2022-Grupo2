import axios from 'axios';
import {put} from './appService';
import Config from '../appConfig.json';

const url = Config.ApiURL;

export async function createUser(data){
    return axios.post(`${url}/authorization`, data);
}

export async function updateUser(data){
    return put(`user/${data.id}`, data); 
}