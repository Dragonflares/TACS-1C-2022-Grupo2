import axios from 'axios';
import Config from '../appConfig.json';

const url = Config.ApiURL;

export function getAuthHeader () {
    const token = localStorage.getItem('jwt');

    return {
        headers: { Authorization: `Bearer ${token}` }
    };
}

export async function post(resource , data , headerExtra = null){
    let config = getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.post(`${url}/${resource}`, data, config); 
}

export async function get(resource, queryParams = null, headerExtra = null){
    let config = getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    let queryString = '';
    if(queryParams !== null){
        queryString = new URLSearchParams(queryParams).toString(); 
    }

    return axios.get(`${url}/${resource}?${queryString}`, config);
}

export async function put(resource, data, headerExtra = null){
    let config = getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.put(`${url}/${resource}`, data, config); 
}

export async function patch(resource, data, headerExtra = null){
    let config = getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.patch(`${url}/${resource}`, data, config); 
}


export async function deleteRes(resource, headerExtra = null){
    let config = getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.delete(`${url}/${resource}`, config);
}

