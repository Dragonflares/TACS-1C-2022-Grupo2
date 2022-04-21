import {put} from './appService';

export async function createUser(data){
    return axios.post(`${url}/authorization`, data);
}

export async function updateUser(data){
    return put(`user/${data.id}`, data); //EJEMPLO TODAVIA NO HAY recurso users
}