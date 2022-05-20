import axios from 'axios';
import Config from '../appConfig.json';

const url = Config.ApiURL;

//USO LOCAL STORAGE PROVISORIAMENTE
export function isAuthenticated(){
    return localStorage.getItem('jwt') ? true : false;
}

//LOCAL STORAGE PROVISIORIO
export function auth(data){
    return axios.post(`${url}/accesstoken`, data).then(
        (response) => {
            if(response.status === 200){
                localStorage.setItem('jwt', response.data.response.token);
                localStorage.setItem('loggedUser', data.username)
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