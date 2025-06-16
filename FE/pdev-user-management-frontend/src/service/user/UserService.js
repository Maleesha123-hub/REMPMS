import axios from 'axios'
import { environment } from '../../environments/environment'

const USER_API_URL = `${environment.baseUrl}` + '/user/v1'

export const getAllWithPagination = (currentPage, size) => {
  try {
    const response = axios.get(`${USER_API_URL}/get-all-page`, {
      params: {
        page: currentPage,
        size: size,
      },
    })
    return response
  } catch (error) {
    throw error
  }
}

export const editUserAccount = (id) => {
  try {
    const response = axios.get(`${USER_API_URL}/get-by-id`, {
      params: {
        userId: id,
      },
    })
    return response
  } catch (error) {
    throw error
  }
}

export const createOrUpdate = async (data) => {
  try {
    const response = await fetch(`${USER_API_URL}/ad/create-or-modify`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
    return await response.json()
  } catch (error) {
    throw error
  }
}
