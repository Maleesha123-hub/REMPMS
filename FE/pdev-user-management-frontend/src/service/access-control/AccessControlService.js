import { environment } from '../../environments/environment'
import axios from 'axios'

const ACCESS_CONTROL_API_URL = `${environment.baseUrl}` + '/access-control/v1'

const api = axios.create({
  baseURL: ACCESS_CONTROL_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const createOrUpdate = async (data) => {
  try {
    const response = await api.post('/create-or-update', data)
    return response
  } catch (error) {
    throw error
  }
}
