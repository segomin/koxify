import axios from 'axios';

export interface User {
    username: string,
    displayName: string,
    password: string,
}

export const signup = (user: User) => {
    return axios.post('/api/1.0/users', user)
}