import React, { use, useEffect, useState } from 'react'
import './UserRole.css'
import {
  createOrUpdate,
  getAllWithPagination,
  getById,
  deleteById,
} from '../../service/user-role/UserRoleService'
import { getAll as getAllAppScopes } from '../../service/application-scope/ApplicationScopeService'
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
  CFormSelect,
  CTableHeaderCell,
  CTableHead,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableRow,
} from '@coreui/react'
import Pagination from '../UI/pagination/Pagination'
import Status from '../constants/Status'

function UserRole() {
  // page response
  const [totalPages, setTotalPages] = useState()
  const [currentPage, setCurrentPage] = useState(0)
  const [userRoles, setUserRoles] = useState([])
  const pageSize = 10

  const [editable, setEditable] = useState(false)
  const [applicationScopes, setApplicationScopes] = useState([])
  const [validated, setValidated] = useState(false)
  const [formData, setFormData] = useState({
    id: '',
    role: '',
    applicationScope: '-1',
    status: '-1',
  })

  useEffect(() => {
    getAllScopse()
  }, [])

  useEffect(() => {
    getAllUserRoles(currentPage, pageSize)
  }, [currentPage])

  const handleSubmit = async (event) => {
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      const updatedForm = { ...formData }
      try {
        const data = await createOrUpdate(updatedForm)
        if (data.status == 'OK') {
          Swal.fire({
            icon: 'success',
            title: 'Success',
            text: data.message,
          })
          getAllUserRoles(currentPage, pageSize)
          handleReset()
        }
      } catch (error) {
        console.error('Error occuring while creating auth party. ', error)
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.response.data.details[1],
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

  const handleReset = () => {
    setFormData({
      id: '',
      role: '',
      status: '-1',
      applicationScope: '-1',
    })
    setEditable(false)
  }

  const getAllUserRoles = async (currentPage, pageSize) => {
    try {
      const data = await getAllWithPagination(currentPage, pageSize)
      if (data.data.status === 'OK') {
        setUserRoles(data.data.data.dataList)
        setTotalPages(data.data.data.totalPages)
        setCurrentPage(data.data.data.currentPage)
      } else {
        Swal.fire({
          icon: 'success',
          title: 'Success',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error('Error occured, while fetching all uesr roles.')
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'User roles fetching failed.',
      })
    }
  }

  const getAllScopse = async () => {
    try {
      const data = await getAllAppScopes()
      if (data.status == 'OK') {
        setApplicationScopes(data.data)
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

  const handleEditUserRole = async (id) => {
    try {
      handleReset()
      const data = await getById(id)
      if (data.data.status === 'OK') {
        const role = data.data.data
        setFormData((prevData) => ({
          ...prevData,
          id: role.userRoleId,
          role: role.role,
          status: role.status,
          applicationScope: role.applicationScope.uniqueId,
        }))
        setEditable(true)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch user role. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'User Role fetching failed.',
      })
    }
  }

  const handleDelete = async (id) => {
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
        if (data.data.status === 'OK') {
          Swal.fire('Deleted!', '', 'success')
          getAllUserRoles(currentPage, pageSize)
          handleReset()
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: data.data.message,
          })
        }
        handleReset()
      } else if (result.isDenied) {
        Swal.fire('Changes are not saved', '', 'info')
      }
    } catch (error) {
      console.error('Error occuring while calling user service to delete uesr role. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: error.response.data.message,
      })
    }
  }

  return (
    <CRow>
      <CCol xs={12}>
        <CCard className="mb-4">
          <CCardHeader>
            <strong>Manage User Roles</strong>
          </CCardHeader>
          <CCardBody>
            <CForm
              className="row gx-3 gy-2 align-items-center"
              noValidate
              validated={validated}
              onSubmit={handleSubmit}
            >
              <CCol sm={3}>
                <CFormLabel htmlFor="specificSizeInputName">Role</CFormLabel>
                <CFormInput
                  id="role"
                  value={formData.role}
                  placeholder="User Role"
                  required
                  onChange={(e) => {
                    handleFormChange(e)
                  }}
                />
                <CFormFeedback tooltip invalid>
                  Please provide a role.
                </CFormFeedback>
              </CCol>
              <CCol sm={3}>
                <CFormLabel htmlFor="specificSizeSelect">Status</CFormLabel>
                <CFormSelect
                  id="status"
                  value={formData.status}
                  style={{ cursor: 'pointer' }}
                  required
                  onChange={(e) => {
                    handleFormChange(e)
                  }}
                >
                  {Status.getAllStatuses().map((staus) => {
                    return (
                      <option key={staus.value} value={staus.value}>
                        {staus.label}
                      </option>
                    )
                  })}
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select a status.
                </CFormFeedback>
              </CCol>
              <CCol sm={3}>
                <CFormLabel htmlFor="specificSizeSelect">Application Scope</CFormLabel>
                <CFormSelect
                  value={formData.applicationScope}
                  id="applicationScope"
                  style={{ cursor: 'pointer' }}
                  required
                  onChange={handleFormChange}
                >
                  <option value="-1">Select an application scope</option>
                  {applicationScopes.map((scope) => (
                    <option key={scope.uniqueId} value={scope.uniqueId}>
                      {scope.scope}
                    </option>
                  ))}
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select an application scope.
                </CFormFeedback>
              </CCol>
              <CCol xs="auto" style={{ marginTop: '40px' }}>
                <CButton color="primary" type="submit">
                  {(editable && 'Update') || 'Create'}
                </CButton>{' '}
                &nbsp;
                <CButton
                  color="success"
                  type="reset"
                  style={{ color: 'white' }}
                  onClick={() => handleReset()}
                >
                  Reset
                </CButton>
                <div className="w-100" id="id" value={formData.id}></div>
              </CCol>
            </CForm>
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={12}>
        <CTable>
          <CTableHead color="dark">
            <CTableRow>
              <CTableHeaderCell scope="col">No</CTableHeaderCell>
              <CTableHeaderCell scope="col">Role</CTableHeaderCell>
              <CTableHeaderCell scope="col">Application Scope</CTableHeaderCell>
              <CTableHeaderCell scope="col">Status</CTableHeaderCell>
              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
            </CTableRow>
          </CTableHead>
          <CTableBody>
            {userRoles.map((userRole, index) => (
              <React.Fragment key={userRole.userRoleId}>
                <CTableRow>
                  <CTableDataCell>{index + 1}</CTableDataCell>
                  <CTableDataCell>{userRole.role}</CTableDataCell>
                  <CTableDataCell>{userRole.applicationScope.scope}</CTableDataCell>
                  <CTableDataCell>{userRole.status}</CTableDataCell>
                  <CTableDataCell>
                    <CButton
                      type="button"
                      className="btn btn-primary btn-sm"
                      onClick={() => handleEditUserRole(userRole.userRoleId)}
                    >
                      <span style={{ color: 'white' }}>Edit</span>
                    </CButton>{' '}
                    &nbsp;
                    <CButton
                      type="button"
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(userRole.userRoleId)}
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

export default UserRole
