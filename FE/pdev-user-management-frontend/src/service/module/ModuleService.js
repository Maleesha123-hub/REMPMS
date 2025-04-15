import axios from 'axios'
import { environment } from '../../environments/environment'

const MODULE_API_URL = `${environment.baseUrl}` + '/module/v1'

export const getAllWithPagination = (currentPage, size) => {
  try {
    const response = axios.get(`${MODULE_API_URL}/get-all-page`, {
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

export const getByUUID = (uuid) => {
  try {
    const response = axios.get(`${MODULE_API_URL}/get-by-uuid`, {
      params: {
        uuid: uuid,
      },
    })
    return response
  } catch (error) {
    throw error
  }
}

export const getAll = () => {
  try {
    const response = axios.get(`${MODULE_API_URL}/get-all`)
    return response
  } catch (error) {
    throw error
  }
}

export const createOrUpdate = async (data) => {
  try {
    const response = await axios.post(`${MODULE_API_URL}/create-or-update`, data, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    return response.data
  } catch (error) {
    throw error
  }
}

export const getModuleById = (id) => {
  try {
    const response = axios.get(`${MODULE_API_URL}/get-by-id`, {
      params: {
        id: id,
      },
    })
    return response
  } catch (error) {
    throw error
  }
}

export const deleteModuleById = (id) => {
  try {
    const response = axios.delete(`${MODULE_API_URL}/delete-by-id`, {
      params: {
        id: id,
      },
    })
    return response
  } catch (error) {
    throw error
  }
}
