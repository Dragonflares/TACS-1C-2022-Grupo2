import axios from 'axios';
import Config from '../appConfig.json';

const url = Config.ApiURL;

export function getUserId(){
    return 1;
}

//USO LOCAL STORAGE PROVISORIAMENTE
export function isAuthenticated(){
    return localStorage.getItem('jwt') ? true : false;
}

//LOCAL STORAGE PROVISIORIO
export async function auth(data){
    return axios.post(`${url}/accesstoken`, data).then(
        async (response) => {
            if(response.status === 200){
                localStorage.setItem('jwt', response.data.response.token);
                return {
                    status :response.status
                }            
            }else{
                return {
                    status : response.status,
                    message: response.statusText
                }
            }
        }
    );
}

export function logOut() {
    localStorage.removeItem('jwt');
}