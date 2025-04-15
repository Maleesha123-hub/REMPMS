import React, { useEffect, useState } from 'react'
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
  CTableHeaderCell,
  CTableHead,
} from '@coreui/react'
import {
  createOrUpdate,
  getAllWithPagination,
  getById,
  deleteById,
} from '../../../service/permission/auth-party-role/AuthPartyRoleService'
import Pagination from '../../UI/pagination/Pagination'
import Swal from 'sweetalert2'

function AuthPartyRole() {
  // page response
  const [totalPages, setTotalPages] = useState()
  const [currentPage, setCurrentPage] = useState(0)
  const [authPartyRoles, setAuthPartyRoles] = useState([])
  const pageSize = 10

  const [editable, setEditable] = useState(false)
  const [validated, setValidated] = useState(false)
  const [formData, setFormData] = useState({
    id: '',
    role: '',
    status: '-1',
  })

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
          getAllAuthPartyRoles(currentPage, pageSize)
          handleReset()
        }
      } catch (error) {
        console.error('Error occuring while creating auth party role. ', error)
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

  useEffect(() => {
    getAllAuthPartyRoles(currentPage, pageSize)
  }, [currentPage])

  const getAllAuthPartyRoles = async (currentPage, pageSize) => {
    try {
      const data = await getAllWithPagination(currentPage, pageSize)
      if (data.data.status === 'OK') {
        setAuthPartyRoles(data.data.data.dataList)
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
      console.error('Error occured, while fetching all auth party roles.')
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Auth party roles fetching failed.',
      })
    }
  }

  const handleEditAuthPartyRole = async (id) => {
    try {
      handleReset()
      const data = await getById(id)
      if (data.data.status === 'OK') {
        const authParty = data.data.data
        setFormData((prevData) => ({
          ...prevData,
          id: authParty.id,
          role: authParty.role,
          status: authParty.status,
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
      console.error('Error occuring while calling user service to fetch auth party role. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Auth party role fetching failed.',
      })
    }
  }

  const handleReset = () => {
    setFormData({
      id: '',
      role: '',
      status: '-1',
    })
    setEditable(false)
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
          getAllAuthPartyRoles(currentPage, pageSize)
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
      console.error('Error occuring while calling user service to delete auth party role. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Auth party role deleting failed.',
      })
    }
  }

  return (
    <CRow>
      <CCol xs={12}>
        <CCard className="mb-4">
          <CCardHeader>
            <strong>Manage Authorize Party Role</strong>
          </CCardHeader>
          <CCardBody>
            <CForm
              className="row gx-3 gy-2 align-items-center"
              noValidate
              validated={validated}
              onSubmit={handleSubmit}
            >
              <CCol sm={4}>
                <CFormLabel htmlFor="role">Role</CFormLabel>
                <CFormInput
                  id="role"
                  value={formData.role}
                  placeholder="Role"
                  required
                  onChange={(e) => {
                    handleFormChange(e)
                  }}
                />
                <CFormFeedback tooltip invalid>
                  Please provide a role.
                </CFormFeedback>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="status">Status</CFormLabel>
                <CFormSelect
                  id="status"
                  value={formData.status}
                  style={{ cursor: 'pointer' }}
                  required
                  onChange={(e) => {
                    handleFormChange(e)
                  }}
                >
                  <option value="-1">Select a status</option>
                  <option value="Active">Active</option>
                  <option value="In_active">In-active</option>
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select a status.
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
              <CTableHeaderCell scope="col">Status</CTableHeaderCell>
              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
            </CTableRow>
          </CTableHead>
          <CTableBody>
            {authPartyRoles.map((partyRole, index) => (
              <React.Fragment key={partyRole.id}>
                <CTableRow>
                  <CTableDataCell>{index + 1}</CTableDataCell>
                  <CTableDataCell>{partyRole.role}</CTableDataCell>
                  <CTableDataCell>{partyRole.status}</CTableDataCell>
                  <CTableDataCell>
                    <CButton
                      type="button"
                      className="btn btn-primary btn-sm"
                      onClick={() => handleEditAuthPartyRole(partyRole.id)}
                    >
                      <span style={{ color: 'white' }}>Edit</span>
                    </CButton>{' '}
                    &nbsp;
                    <CButton
                      type="button"
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(partyRole.id)}
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

export default AuthPartyRole
