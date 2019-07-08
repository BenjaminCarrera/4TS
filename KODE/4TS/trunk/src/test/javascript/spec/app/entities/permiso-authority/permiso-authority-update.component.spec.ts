/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { PermisoAuthorityUpdateComponent } from 'app/entities/permiso-authority/permiso-authority-update.component';
import { PermisoAuthorityService } from 'app/entities/permiso-authority/permiso-authority.service';
import { PermisoAuthority } from 'app/shared/model/permiso-authority.model';

describe('Component Tests', () => {
  describe('PermisoAuthority Management Update Component', () => {
    let comp: PermisoAuthorityUpdateComponent;
    let fixture: ComponentFixture<PermisoAuthorityUpdateComponent>;
    let service: PermisoAuthorityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [PermisoAuthorityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PermisoAuthorityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PermisoAuthorityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermisoAuthorityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PermisoAuthority(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PermisoAuthority();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
