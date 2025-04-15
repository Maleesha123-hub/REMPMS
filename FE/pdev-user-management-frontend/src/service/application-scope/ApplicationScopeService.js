import { environment } from '../../environments/environment'

const APPLICATION_SCOPE_API_URL = `${environment.baseUrl}` + '/application-scope/v1'

export const getAll = async () => {
  try {
    const response = await fetch(`${APPLICATION_SCOPE_API_URL}/get-all`)
    return await response.json()
  } catch (error) {
    throw error
  }
}

export const getAllWithPagination = async (currentPage, pageSize) => {
  try {
    const url = new URL(`${APPLICATION_SCOPE_API_URL}/get-all-page`)
    url.searchParams.append('page', currentPage)
    url.searchParams.append('size', pageSize)

    const response = await fetch(url.toString(), {
      method: 'GET',
    })

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`)
    }

    return await response.json()
  } catch (error) {
    throw error
  }
}

export const getById = async (id) => {
  try {
    const url = new URL(`${APPLICATION_SCOPE_API_URL}/get-by-id`)
    url.searchParams.append('id', id)

    const response = await fetch(url.toString(), {
      method: 'GET',
    })

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`)
    }

    return await response.json()
  } catch (error) {
    throw error
  }
}

export const deleteById = async (id) => {
  try {
    const url = new URL(`${APPLICATION_SCOPE_API_URL}/delete-by-id`)
    url.searchParams.append('id', id)

    const response = await fetch(url.toString(), {
      method: 'DELETE',
    })

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`)
    }

    return await response.json()
  } catch (error) {
    throw error
  }
}

export const createOrUpdate = async (data) => {
  try {
    const response = await fetch(`${APPLICATION_SCOPE_API_URL}/create-or-update`, {
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
