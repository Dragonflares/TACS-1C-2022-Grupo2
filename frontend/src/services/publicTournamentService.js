import {get} from './appService'
import { getAuthHeader } from './appService';

const resource = 'tournaments';

export async function getPublicTournaments(offset, limit){
    return get(`${resource}`, {
        offset: offset,
        limit: limit
    });
}