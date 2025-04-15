import axios from 'axios'
import { environment } from '../../environments/environment'

const COMPONENT_ELEMENT_API_URL = `${environment.baseUrl}` + '/component-element/v1'

const api = axios.create({
  baseURL: COMPONENT_ELEMENT_API_URL,
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

export const getAllWithPagination = (currentPage, size) => {
  try {
    const response = api.get('/get-all-page', {
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

export const getComponentElementById = (id) => {
  try {
    const response = axios.get(`${COMPONENT_ELEMENT_API_URL}/get-by-id`, {
      params: {
        id: id,
      },
    })
    return response
  } catch (error) {
    throw error
  }
}

export const deleteComponentElementById = (id) => {
  try {
    const response = axios.delete(`${COMPONENT_ELEMENT_API_URL}/delete-by-id`, {
      params: {
        id: id,
      },
    })
    return response
  } catch (error) {
    throw error
  }
}

export const getComponentElementsByScopeAndComponents = (scope, components) => {
  try {
    const response = axios.get(`${COMPONENT_ELEMENT_API_URL}/get-by-scope-and-components`, {
      params: {
        scope: scope,
        components: components.join(','),
      },
    })
    return response
  } catch (error) {
    throw error
  }
}
