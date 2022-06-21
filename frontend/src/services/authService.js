import axios from 'axios';
import Config from '../appConfig.json';

const url = Config.ApiURL;

//USO LOCAL STORAGE PROVISORIAMENTE
export function isAuthenticated(){
    return sessionStorage.getItem('jwt') ? true : false;
}

//LOCAL STORAGE PROVISIORIO
export function auth(data){
    return axios.post(`${url}/accesstoken`, data).then(
        (response) => {
            if(response.status === 200){
                sessionStorage.setItem('jwt', response.data.response.token);
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
    sessionStorage.removeItem('jwt');
}