import axios from 'axios';
import Config from '../appConfig.json';

const url = Config.ApiURL;

//CONSULTAR SI VALIDAR OPTIONS ANTES DE MANDAR EL REQUEST

//USO LOCAL STORAGE PROVISORIAMENTE
export function IsAuthenticated(){
    return localStorage.getItem('jwt') ? true : false;
}

function getAuthHeader () {
    const token = localStorage.getItem('jwt');

    return {
        headers: { Authorization: `Bearer ${token}` }
    };
}

export async function Post(resource , data , headerExtra = null){
    config = this.getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.post(`${url}/${resource}`, data, config); 
}

export async function Get(resource, queryParams = null, headerExtra = null){
    config = this.getAuthHeader();

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

export async function Put(resource, data, headerExtra = null){
    config = this.getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.put(`${url}/${resource}`, data, config); 
}

export async function Patch(resource, data, headerExtra = null){
    config = this.getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.patch(`${url}/${resource}`, data, config); 
}


export async function Delete(resource, headerExtra = null){
    config = this.getAuthHeader();

    if(headerExtra !== null){
        config.headers = {
            ...config.headers,
            headerExtra
        };
    }

    return axios.delete(`${url}/${resource}`, config);
}

