import React, { useState, useEffect } from 'react'
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CForm,
  CFormInput,
  CInputGroup,
  CFormCheck,
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
  getAllWithPagination,
  createOrUpdate,
  getAuthProfileByAuthPartyId,
  deleteById,
} from '../../../service/permission/auth-party-profile/AuthPartyProfileService'
import { getAllAuthParties } from '../../../service/permission/auth-party/AuthPartyService'
import { getAllAuthRoles } from '../../../service/permission/auth-party-role/AuthPartyRoleService'
import Swal from 'sweetalert2'
import Pagination from '../../UI/pagination/Pagination'

function AuthPartyProfile() {
  // page response
  const [totalPages, setTotalPages] = useState()
  const [currentPage, setCurrentPage] = useState(0)
  const [authPartyProfiles, setAuthPartyProfiles] = useState([])
  const pageSize = 10

  const [editable, setEditable] = useState(false)
  const [selectedPartyRoles, setSelectedPartyRoles] = useState([])
  const [authPartyRoles, setAuthPartyRoles] = useState([])
  const [authParties, setAuthParties] = useState([])
  const [validated, setValidated] = useState(false)
  const [formData, setFormData] = useState({
    authorizeParty: '-1',
    authorizePartyRoles: [],
  })

  useEffect(() => {
    getAllAuthorizeParties()
    getAllAuthorizePartyRoles()
  }, [])

  const getAllAuthorizePartyRoles = async () => {
    try {
      const data = await getAllAuthRoles()
      if (data.data.status == 'OK') {
        setAuthPartyRoles(data.data.data)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error(
        'Error occuring while calling user service to fetch all auth party profiles. ',
        error,
      )
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Auth party profiles fetching failed.',
      })
    }
  }

  const getAuthProfilesByAuthPartyId = async (id) => {
    setSelectedPartyRoles([])
    if (id !== '-1') {
      try {
        const data = await getAuthProfileByAuthPartyId(id)
        if (data.data.status === 'OK') {
          setSelectedPartyRoles(data.data.data.authorizePartyRolesIdList)
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: data.data.message,
          })
        }
      } catch (error) {
        console.error(
          'Error occuring while calling user service to fetch all auth party profile by auth party. ',
          error,
        )
        Swal.fire({
          icon: 'error',
          title: 'Internal Server Error',
          text: 'Auth party profiles fetching failed.',
        })
      }
    }
  }

  const getAllAuthorizeParties = async () => {
    try {
      const data = await getAllAuthParties()
      if (data.data.status === 'OK') {
        setAuthParties(data.data.data)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch all auth parties. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Auth parties fetching failed.',
      })
    }
  }

  useEffect(() => {
    getAllProfiles(currentPage, pageSize)
  }, [currentPage])

  const getAllProfiles = async (currentPage, pageSize) => {
    try {
      const data = await getAllWithPagination(currentPage, pageSize)
      if (data.data.status === 'OK') {
        setAuthPartyProfiles(data.data.data.dataList)
        setTotalPages(data.data.data.totalPages)
        setCurrentPage(data.data.data.currentPage)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error(
        'Error occuring while calling user service to fetch all auth party roles. ',
        error,
      )
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Auth party roles fetching failed.',
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
      const updatedForm = { ...formData }
      updatedForm.authorizePartyRoles = selectedPartyRoles
      try {
        const data = await createOrUpdate(updatedForm)
        if (data.status === 'OK') {
          getAllProfiles(currentPage, pageSize)
          Swal.fire({
            icon: 'success',
            title: 'Success',
            text: data.message,
          })
          getAllProfiles(currentPage, pageSize)
          handleReset()
        }
      } catch (error) {
        console.error('Error occuring while creating component elements. ', error)
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

  const handleAddCheckChange = (e) => {
    const roleId = e.target.id
    setSelectedPartyRoles((prevData) => {
      const updatedArr = [...prevData]
      const isRoleAlreadySelected = updatedArr.includes(roleId)

      if (isRoleAlreadySelected) {
        // If it's already selected, remove it (uncheck)
        return updatedArr.filter((id) => id !== roleId)
      } else {
        // Otherwise, add it to the array (check)
        updatedArr.push(roleId)
        return updatedArr
      }
    })
  }

  const handleEditAuthPartyProfile = async (id) => {
    try {
      handleReset()
      const data = await getAuthProfileByAuthPartyId(id)
      if (data.data.status === 'OK') {
        const authPartyProfile = data.data.data
        setFormData((prevData) => ({
          ...prevData,
          authorizeParty: authPartyProfile.authorizePartyId,
        }))
        setSelectedPartyRoles(authPartyProfile.authorizePartyRolesIdList)
        setEditable(true)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error(
        'Error occuring while calling user service to fetch auth party profiles. ',
        error,
      )
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Auth party profiles fetching failed.',
      })
    }
  }

  const handleReset = () => {
    setFormData({
      authorizeParty: '',
    })
    setSelectedPartyRoles([])
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
          getAllProfiles(currentPage, pageSize)
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
      console.error(
        'Error occuring while calling user service to delete auth party profiles. ',
        error,
      )
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Auth party profiles deleting failed.',
      })
    }
  }

  return (
    <CRow>
      <CCol xs={12}>
        <CCard className="mb-4">
          <CCardHeader>
            <strong>Manage Authorize Party Profile</strong>
          </CCardHeader>
          <CCardBody>
            <CForm
              className="row gx-3 gy-2 align-items-center"
              noValidate
              validated={validated}
              onSubmit={handleSubmit}
            >
              <CCol sm={4}>
                <CFormLabel htmlFor="authorizeParty">Authorize Party</CFormLabel>
                <CFormSelect
                  id="authorizeParty"
                  value={formData.authorizeParty}
                  style={{ cursor: 'pointer' }}
                  required
                  onChange={(e) => {
                    handleFormChange(e)
                    getAuthProfilesByAuthPartyId(e.target.value)
                  }}
                >
                  <option value="-1">Select an authorize party</option>
                  {authParties.map((a) => (
                    <option key={a.authorizePartyId} value={a.authorizePartyId}>
                      {a.party}
                    </option>
                  ))}
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select an authorize party.
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
              </CCol>
            </CForm>
            <CCard className="mb-4" style={{ marginTop: '20px' }}>
              <CCardHeader>
                <span>Authorize Party Role</span>
              </CCardHeader>
              <CCardBody>
                <CCol sm={14}>
                  <CInputGroup>
                    {authPartyRoles.map((r) => (
                      <React.Fragment key={r.id}>
                        <CFormCheck
                          type="checkbox"
                          id={r.id}
                          label={r.role}
                          style={{ cursor: 'pointer' }}
                          onChange={(e) => {
                            handleAddCheckChange(e)
                          }}
                          checked={selectedPartyRoles.includes(r.id.toString())}
                        />{' '}
                        &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                      </React.Fragment>
                    ))}
                  </CInputGroup>
                </CCol>
              </CCardBody>
            </CCard>
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={12}>
        <CTable>
          <CTableHead color="dark">
            <CTableRow>
              <CTableHeaderCell scope="col">No</CTableHeaderCell>
              <CTableHeaderCell scope="col">Party</CTableHeaderCell>
              <CTableHeaderCell scope="col">Roles</CTableHeaderCell>
              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
            </CTableRow>
          </CTableHead>
          <CTableBody>
            {authPartyProfiles.map((profile, index) => (
              <React.Fragment key={profile.authorizePartyId}>
                <CTableRow>
                  <CTableDataCell>{index + 1}</CTableDataCell>
                  <CTableDataCell>{profile.authorizeParty.party}</CTableDataCell>
                  <CTableDataCell>{profile.authorizePartyRoles}</CTableDataCell>
                  <CTableDataCell>
                    <CButton
                      type="button"
                      className="btn btn-primary btn-sm"
                      onClick={() => handleEditAuthPartyProfile(profile.authorizePartyId)}
                    >
                      <span style={{ color: 'white' }}>Edit</span>
                    </CButton>{' '}
                    &nbsp;
                    <CButton
                      type="button"
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(profile.authorizePartyId)}
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

export default AuthPartyProfile
