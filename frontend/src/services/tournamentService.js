import {deleteRes, get, post, patch} from './appService'
import { getAuthHeader } from './appService';

const resource = 'tournaments';
const participantsSubResource = 'participants';
const nonParticipantsSubResource = 'nonParticipants';
const positionsSubResource = 'positions';


export async function getPublicTournaments(offset, limit){
    return get(`${resource}`, {
        offset: offset,
        limit: limit
    });
}

export async function getTournament(id){
    return get(`${resource}/${id}`);
}

export async function createTournament(data){
    return post(`${resource}`, data);
}

export async function updateTournament(id, data){
    return patch(`${resource}/${id}`, data);
}

export async function deleteTournament(id){
    return deleteRes(`${resource}/${id}`);
}

export async function getNonParticipants(tournamentId, search){
    return get(`${resource}/${tournamentId}/${nonParticipantsSubResource}`, {
        search: search
    });
}

export async function getParticipants(tournamentId, offset, limit){
    return get(`${resource}/${tournamentId}/${participantsSubResource}`, {
        offset: offset,
        limit: limit
    });
}

export async function addParticipants(tournamentId, data){
    return post(`${resource}/${tournamentId}/${participantsSubResource}`, data);
}

export async function getPositions(tournamentId, offset, limit){
    return get(`${resource}/${tournamentId}/${positionsSubResource}`, {
        offset: offset,
        limit: limit
    });
}