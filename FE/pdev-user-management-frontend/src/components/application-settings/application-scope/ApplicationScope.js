import React, { useEffect, useState, useRef } from 'react'
import Swal from 'sweetalert2'
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CFormFeedback,
  CRow,
  CTableRow,
  CFormSelect,
  CTable,
  CTableBody,
  CTableDataCell,
  CInputGroup,
  CTableHeaderCell,
  CTableHead,
} from '@coreui/react'
import {
  getAllWithPagination,
  createOrUpdate,
  getById,
  deleteById,
} from '../../../service/application-scope/ApplicationScopeService'
import Pagination from '../../UI/pagination/Pagination'

const ApplicationScope = () => {
  // page response
  const [totalPages, setTotalPages] = useState()
  const [currentPage, setCurrentPage] = useState(0)
  const [applicationScopes, setApplicationScopes] = useState([])
  const pageSize = 10

  const [validated, setValidated] = useState(false)
  const [formData, setFormData] = useState({
    applicationScopeId: '',
    scope: '',
    status: undefined,
    uniqueId: '',
  })

  useEffect(() => {
    getAllAppScopes(currentPage, pageSize)
  }, [currentPage])

  const getScopeById = async (id) => {
    try {
      const data = await getById(id)
      if (data.status == 'OK') {
        setFormData((prevData) => ({
          ...prevData,
          applicationScopeId: data.data.applicationScopeId,
          scope: data.data.scope,
          status: data.data.status,
          uniqueId: data.data.uniqueId,
        }))
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch scope by id. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Application scope fetching failed.',
      })
    }
  }

  const deleteScopeById = async (id) => {
    try {
      const result = await Swal.fire({
        title: 'Do you want to delete?',
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: 'Delete',
        denyButtonText: `Don't delete`,
      })

      if (result.isConfirmed) {
        const data = await deleteById(id)
        if (data.status === 'OK') {
          Swal.fire('Deleted!', '', 'success')
          getAllAppScopes(currentPage, pageSize)
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: data.message,
          })
        }
      } else if (result.isDenied) {
        Swal.fire('Changes are not saved', '', 'info')
      }
    } catch (error) {
      console.error('Error occurred while calling user service to delete scope by id.', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Application scope deleting failed.',
      })
    }
  }

  const getAllAppScopes = async (currentPage, pageSize) => {
    try {
      const data = await getAllWithPagination(currentPage, pageSize)
      if (data.status == 'OK') {
        setApplicationScopes(data.data.dataList)
        setTotalPages(data.data.totalPages)
        setCurrentPage(data.data.currentPage)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch all scopes. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Application scopes fetching failed.',
      })
    }
  }

  const handleSubmit = async (event) => {
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      try {
        const data = await createOrUpdate(formData)
        if (data.status == 'OK') {
          Swal.fire({
            icon: 'success',
            title: 'Success',
            text: data.message,
          })
          getAllAppScopes(currentPage, pageSize)
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: data.message,
          })
        }
      } catch (error) {
        console.error('Error occuring while creating scope. ', error)
        Swal.fire({
          icon: 'error',
          title: 'Internal Server Error',
          text: 'Application scope creation failed.',
        })
      }
    }
  }

  const handleFormChange = (e) => {
    const { id, value } = e.target
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }))
  }
  return (
    <CRow>
      <CCol xs={12}>
        <CCard className="mb-4">
          <CCardHeader>
            <strong>Manage Application Scope</strong>
          </CCardHeader>
          <CCardBody>
            <CForm
              className="row gx-3 gy-2 align-items-center"
              noValidate
              validated={validated}
              onSubmit={handleSubmit}
            >
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputName">Scope</CFormLabel>
                <CFormInput
                  id="scope"
                  placeholder="Scope"
                  value={formData.scope}
                  onChange={handleFormChange}
                  required
                />
                <CFormFeedback tooltip invalid>
                  Please provide a scope.
                </CFormFeedback>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="uniqueId">Unique ID</CFormLabel>
                <CInputGroup>
                  <CFormInput
                    id="uniqueId"
                    placeholder="Unique ID"
                    value={formData.uniqueId}
                    disabled
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide a unique id.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="status">Status</CFormLabel>
                <CFormSelect
                  id="status"
                  style={{ cursor: 'pointer' }}
                  value={formData.status}
                  onChange={handleFormChange}
                  required
                >
                  <option value="-1">Select a status</option>
                  <option value="Active">Active</option>
                  <option value="In_active">In-active</option>
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select a status.
                </CFormFeedback>
              </CCol>
              <div className="w-100"></div>
              <CRow className="justify-content-end">
                <CCol xs="auto" style={{ marginTop: '30px' }}>
                  <CButton color="primary" type="submit">
                    Create
                  </CButton>
                </CCol>
              </CRow>
            </CForm>
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={12}>
        <CTable>
          <CTableHead color="dark">
            <CTableRow>
              <CTableHeaderCell scope="col">No</CTableHeaderCell>
              <CTableHeaderCell scope="col">Scope</CTableHeaderCell>
              <CTableHeaderCell scope="col">Unique ID</CTableHeaderCell>
              <CTableHeaderCell scope="col">Status</CTableHeaderCell>
              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
            </CTableRow>
          </CTableHead>
          <CTableBody>
            {applicationScopes.map((scope, index) => (
              <React.Fragment key={scope.applicationScopeId}>
                <CTableRow>
                  <CTableDataCell>{index + 1}</CTableDataCell>
                  <CTableDataCell>{scope.scope}</CTableDataCell>
                  <CTableDataCell>{scope.uniqueId}</CTableDataCell>
                  <CTableDataCell>{scope.status}</CTableDataCell>
                  <CTableDataCell>
                    <CButton
                      type="button"
                      className="btn btn-primary btn-sm"
                      onClick={() => {
                        getScopeById(scope.applicationScopeId)
                      }}
                    >
                      <span style={{ color: 'white' }}>Edit</span>
                    </CButton>{' '}
                    &nbsp;
                    <CButton
                      type="button"
                      className="btn btn-danger btn-sm"
                      onClick={() => {
                        deleteScopeById(scope.applicationScopeId)
                      }}
                    >
                      <span style={{ color: 'white' }}>Delete</span>
                    </CButton>{' '}
                  </CTableDataCell>
                </CTableRow>
              </React.Fragment>
            ))}
          </CTableBody>
        </CTable>
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      </CCol>
    </CRow>
  )
}
export default ApplicationScope
