import React, { useEffect, useState, useRef } from 'react'
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
  CFormCheck,
  CFormSelect,
  CInputGroup,
  CInputGroupText,
  CTableHeaderCell,
  CTableHead,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableRow,
} from '@coreui/react'
import {
  getAllWithPagination,
  createOrUpdate,
  editUserAccount,
} from '../../service/user/UserService'
import { getAll as getAllApplicationScopes } from '../../service/application-scope/ApplicationScopeService'
import { getAllUserRolesByScope } from '../../service/user-role/UserRoleService'
import { getAllAuthParties } from '../../service/permission/auth-party/AuthPartyService'
import Swal from 'sweetalert2'

const User = () => {
  const [validated, setValidated] = useState(false)

  // page response
  const [totalPages, setTotalPages] = useState()
  const [currentPage, setCurrentPage] = useState(0)
  const [users, setUsers] = useState([])
  const pageSize = 10

  const userRoleRef = useRef(null)
  const applicationScopeRef = useRef(null)
  const [emailVerified, setEmailVerified] = useState(false)
  const [isLocked, setIsLocked] = useState(false)

  const [applicationScope, setApplicationScope] = useState([])
  const [userRole, setUserRole] = useState([])
  const [authParties, setAuthParties] = useState([])
  const [selectedParties, setSelectedParties] = useState([])
  const [selectedRolesAndScopes, setSelectedRolesAndScopes] = useState([])
  const [formData, setFormData] = useState({
    userId: '',
    email: '',
    firstName: '',
    lastName: '',
    userName: '',
    password: '',
    uuid: '',
    status: '',
    emailVerified: null,
    locked: null,
    userHasAuthorizePartyIds: selectedParties,
    userHasApplicationScopeHasUserRoles: selectedRolesAndScopes,
  })

  useEffect(() => {
    loadUsersPage()
    loadApplicationScope()
    loadAuthParties()
  }, [])

  useEffect(() => {
    setFormData((prevData) => ({
      ...prevData,
      emailVerified: emailVerified,
      locked: isLocked,
      userHasAuthorizePartyIds: selectedParties,
      userHasApplicationScopeHasUserRoles: selectedRolesAndScopes,
    }))
    console.log(formData)
  }, [emailVerified, isLocked, selectedParties, selectedRolesAndScopes])

  const loadUsersPage = async () => {
    try {
      const data = await getAllWithPagination(currentPage, pageSize)
      if (data.data.status == 'OK') {
        setUsers(data.data.data.dataList)
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
      console.error('Error occuring while calling user service to fetch all users. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Users fetching failed.',
      })
    }
  }

  const loadApplicationScope = async () => {
    try {
      const data = await getAllApplicationScopes()
      if (data.status == 'OK') {
        setApplicationScope(data.data)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch all app scopes. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Application scopes fetching failed.',
      })
    }
  }

  const loadUserRolesByAppScope = async (e) => {
    setUserRole([])
    try {
      const uuid = e.target.value
      const data = await getAllUserRolesByScope(uuid)
      if (data.data.status == 'OK') {
        setUserRole(data.data.data)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch all user roles. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'User roles fetching failed.',
      })
    }
  }

  const loadAuthParties = async () => {
    try {
      const data = await getAllAuthParties()
      if (data.data.status == 'OK') {
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

  const handleCheckAuthParties = (e) => {
    setSelectedParties((prevSelectedParties) => {
      const selectedParty = e.target.id
      if (prevSelectedParties.includes(selectedParty)) {
        return prevSelectedParties.filter((party) => party !== selectedParty)
      } else {
        return [...prevSelectedParties, selectedParty]
      }
    })
  }

  const loadSelectedUserRolesTempTable = (e) => {
    const roleId = userRoleRef.current.value
    const scopeUUID = applicationScopeRef.current.value
    const roleName = userRole.find((r) => r.userRoleId == roleId)?.role
    const scopeName = applicationScope.find((a) => a.uniqueId == scopeUUID)?.scope

    setSelectedRolesAndScopes((prevRolesAndScopes) => {
      const obj = {
        userRoleId: roleId,
        scopeUUID: scopeUUID,
        roleName: roleName,
        scopeName: scopeName,
      }
      return [...prevRolesAndScopes, obj]
    })
  }

  const removeSelectedRoleAndAppScope = (id) => {
    setSelectedRolesAndScopes((prevRolesAndScopes) => {
      return prevRolesAndScopes.filter((obj) => obj.userRoleId !== id)
    })
  }

  const handleSubmit = async (event) => {
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      try {
        console.log('Creating...')
        console.log(formData)
        const data = await createOrUpdate(formData)
        if (data.status == 'OK') {
          Swal.fire({
            icon: 'success',
            title: 'Success',
            text: data.message,
          })
          loadUsersPage()
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: data.message,
          })
        }
      } catch (error) {
        console.error('Error occuring while creating user account. ', error)
        Swal.fire({
          icon: 'error',
          title: 'Internal Server Error',
          text: 'User account creation failed.',
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

  const handleEditUserAccount = async (id) => {
    try {
      const data = await editUserAccount(id)
      if (data.data.status == 'OK') {
        setSelectedRolesAndScopes([])
        setSelectedParties([])

        setEmailVerified(data.data.data.isEmailVerified)
        setIsLocked(data.data.data.isLocked)
        setFormData((prevData) => ({
          ...prevData,
          email: data.data.data.email,
          firstName: data.data.data.firstName,
          lastName: data.data.data.lastName,
          userName: data.data.data.userName,
          status: data.data.data.status,
        }))

        setSelectedParties(data.data.data.userAuthParties.map((r) => r.toString()))
        data.data.data.userHasApplicationScopeHasUserRoles.map((r) => {
          setSelectedRolesAndScopes((prevRolesAndScopes) => {
            const obj = {
              userRoleId: r.userRole?.userRoleId,
              scopeUUID: r.userRole?.applicationScope?.uniqueId,
              roleName: r.userRole?.role,
              scopeName: r.userRole?.applicationScope?.scope,
            }
            return [...prevRolesAndScopes, obj]
          })
        })
      }
    } catch (error) {
      console.error('Error occuring while editing user account. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'User account editing failed.',
      })
    }
  }

  return (
    <CRow>
      <CCol xs={12}>
        <CCard className="mb-4">
          <CCardHeader>
            <strong>Manage Users</strong>
          </CCardHeader>
          <CCardBody>
            <CForm
              className="row gx-3 gy-2 align-items-center"
              noValidate
              validated={validated}
              onSubmit={handleSubmit}
            >
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputName">First Name</CFormLabel>
                <CFormInput
                  id="firstName"
                  value={formData.firstName}
                  placeholder="First Name"
                  required
                  onChange={(e) => handleFormChange(e)}
                />
                <CFormFeedback tooltip invalid>
                  Please provide a first name.
                </CFormFeedback>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Last Name</CFormLabel>
                <CInputGroup>
                  <CFormInput
                    id="lastName"
                    value={formData.lastName}
                    placeholder="Last Name"
                    required
                    onChange={(e) => handleFormChange(e)}
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide a last name.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">User Name</CFormLabel>
                <CInputGroup className="has-validation">
                  <CInputGroupText>@</CInputGroupText>
                  <CFormInput
                    id="userName"
                    value={formData.userName}
                    placeholder="User Name"
                    required
                    onChange={(e) => handleFormChange(e)}
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide a user name.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Password</CFormLabel>
                <CInputGroup>
                  <CFormInput
                    type="password"
                    id="password"
                    placeholder="Password"
                    onChange={(e) => handleFormChange(e)}
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide a password.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Email</CFormLabel>
                <CInputGroup>
                  <CFormInput
                    id="email"
                    value={formData.email}
                    placeholder="Email"
                    required
                    onChange={(e) => handleFormChange(e)}
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide an email.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeSelect">Status</CFormLabel>
                <CFormSelect
                  id="status"
                  value={formData.status}
                  style={{ cursor: 'pointer' }}
                  required
                  onChange={(e) => handleFormChange(e)}
                >
                  <option value="-1">Select a status</option>
                  <option value="Active">Active</option>
                  <option value="In_active">In-active</option>
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select a status.
                </CFormFeedback>
              </CCol>
              <CCol sm={2}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Email verification</CFormLabel>
                <CInputGroup>
                  <CFormCheck
                    type="checkbox"
                    id="emailVerified"
                    label="Verified"
                    onChange={(e) => setEmailVerified(e.target.checked)}
                    checked={emailVerified}
                    style={{ cursor: 'pointer' }}
                  />
                </CInputGroup>
              </CCol>
              <CCol sm={2}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Lock user account</CFormLabel>
                <CInputGroup>
                  <CFormCheck
                    type="checkbox"
                    id="locked"
                    label="Locked"
                    onChange={(e) => setIsLocked(e.target.checked)}
                    checked={isLocked}
                    style={{ cursor: 'pointer' }}
                  />
                </CInputGroup>
              </CCol>
              <br />
              <br />
              <br />
              <br />
              <CCol xs={12}>
                <CCard className="mb-4">
                  <CCardHeader>
                    <span>Authorize Parties</span>
                  </CCardHeader>
                  <CCardBody>
                    <CCol sm={6}>
                      <CInputGroup>
                        {authParties.map((r) => (
                          <React.Fragment key={r.authorizePartyId}>
                            <CFormCheck
                              type="checkbox"
                              id={r.authorizePartyId}
                              label={r.party}
                              style={{ cursor: 'pointer' }}
                              onChange={(e) => {
                                handleCheckAuthParties(e)
                              }}
                              checked={selectedParties.includes(r.authorizePartyId.toString())}
                            />{' '}
                            &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                          </React.Fragment>
                        ))}
                      </CInputGroup>
                    </CCol>
                  </CCardBody>
                </CCard>
              </CCol>
              <br />
              <br />
              <br />
              <br />
              <CCol xs={12}>
                <CCard className="mb-4">
                  <CCardHeader>
                    <span>Application & Roles</span>
                  </CCardHeader>
                  <CCardBody>
                    <div className="row gx-3 gy-2 align-items-center">
                      <CCol sm={4}>
                        <CFormLabel htmlFor="specificSizeSelect">Application Scope</CFormLabel>
                        <CFormSelect
                          id="applicationScope"
                          style={{ cursor: 'pointer' }}
                          onChange={(event) => loadUserRolesByAppScope(event)}
                          required
                          ref={applicationScopeRef}
                        >
                          <option value="-1">Select an application scope</option>
                          {applicationScope.map((scope) => (
                            <option key={scope.uniqueId} value={scope.uniqueId}>
                              {scope.scope}
                            </option>
                          ))}
                        </CFormSelect>
                        <CFormFeedback tooltip invalid>
                          Please select an application scope.
                        </CFormFeedback>
                      </CCol>
                      <CCol sm={4}>
                        <CFormLabel htmlFor="specificSizeSelect">User Role</CFormLabel>
                        <CFormSelect
                          id="userRole"
                          style={{ cursor: 'pointer' }}
                          required
                          ref={userRoleRef}
                        >
                          <option value="-1">Select a user role</option>
                          {userRole.map((role, index) => {
                            return (
                              <option key={index} value={role.userRoleId}>
                                {role.role}
                              </option>
                            )
                          })}
                        </CFormSelect>
                        <CFormFeedback tooltip invalid>
                          Please select a user role.
                        </CFormFeedback>
                      </CCol>
                      <CCol sm={4}>
                        <CCol xs="auto" style={{ marginTop: '40px' }}>
                          <CButton
                            type="button"
                            className="btn btn-success btn-sm"
                            onClick={(e) => loadSelectedUserRolesTempTable(e)}
                          >
                            <strong style={{ color: 'white' }}>+</strong>
                          </CButton>
                        </CCol>
                      </CCol>
                      <br />
                      <br />
                      <br />
                      <br />
                      <CCol xs={12}>
                        <CTable>
                          <CTableHead color="dark">
                            <CTableRow>
                              <CTableHeaderCell scope="col">No</CTableHeaderCell>
                              <CTableHeaderCell scope="col">Scope</CTableHeaderCell>
                              <CTableHeaderCell scope="col">Role</CTableHeaderCell>
                              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
                            </CTableRow>
                          </CTableHead>
                          <CTableBody>
                            {selectedRolesAndScopes.map((obj, index) => (
                              <CTableRow key={obj.userRoleId || `${obj.scopeUUID}-${obj.roleName}`}>
                                <CTableDataCell>{index + 1}</CTableDataCell>
                                <CTableDataCell>{obj.scopeName}</CTableDataCell>
                                <CTableDataCell>{obj.roleName}</CTableDataCell>
                                <CTableDataCell>
                                  <span
                                    className="btn btn-danger btn-sm"
                                    style={{ color: 'white' }}
                                    onClick={() => {
                                      removeSelectedRoleAndAppScope(obj.userRoleId)
                                    }}
                                  >
                                    Remove
                                  </span>
                                </CTableDataCell>
                              </CTableRow>
                            ))}
                          </CTableBody>
                        </CTable>
                      </CCol>
                    </div>
                  </CCardBody>
                </CCard>
              </CCol>
              <CCol xs={11} />
              <CCol xs="auto">
                <CButton color="primary" type="submit">
                  Create
                </CButton>
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
              <CTableHeaderCell scope="col">First name</CTableHeaderCell>
              <CTableHeaderCell scope="col">Last name</CTableHeaderCell>
              <CTableHeaderCell scope="col">User name</CTableHeaderCell>
              <CTableHeaderCell scope="col">Email</CTableHeaderCell>
              <CTableHeaderCell scope="col">Email verify</CTableHeaderCell>
              <CTableHeaderCell scope="col">Locked</CTableHeaderCell>
              <CTableHeaderCell scope="col">Status</CTableHeaderCell>
              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
            </CTableRow>
          </CTableHead>
          <CTableBody>
            {users.map((user, index) => (
              <React.Fragment key={user.idUser}>
                <CTableRow>
                  <CTableDataCell>{index + 1}</CTableDataCell>
                  <CTableDataCell>{user.firstName}</CTableDataCell>
                  <CTableDataCell>{user.lastName}</CTableDataCell>
                  <CTableDataCell>{user.userName}</CTableDataCell>
                  <CTableDataCell>{user.email}</CTableDataCell>
                  <CTableDataCell>
                    {user.isEmailVerified ? 'Verified' : 'Un-verified'}
                  </CTableDataCell>
                  <CTableDataCell>{user.accountNonLocked ? 'Non-locked' : 'Locked'}</CTableDataCell>
                  <CTableDataCell>{user.status}</CTableDataCell>
                  <CTableDataCell>
                    <CButton
                      type="button"
                      className="btn btn-primary btn-sm"
                      onClick={() => handleEditUserAccount(user.idUser)}
                    >
                      <span style={{ color: 'white' }}>Edit</span>
                    </CButton>{' '}
                    &nbsp;
                    <CButton type="button" className="btn btn-danger btn-sm">
                      <span style={{ color: 'white' }}>Delete</span>
                    </CButton>{' '}
                  </CTableDataCell>
                </CTableRow>
              </React.Fragment>
            ))}
          </CTableBody>
        </CTable>
      </CCol>
    </CRow>
  )
}

export default User
